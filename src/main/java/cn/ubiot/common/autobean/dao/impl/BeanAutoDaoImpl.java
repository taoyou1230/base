package cn.ubiot.common.autobean.dao.impl;


import cn.ubiot.common.autobean.bean.ColumnStruct;
import cn.ubiot.common.autobean.bean.TableStruct;
import cn.ubiot.common.autobean.dao.BeanAutoDao;
import cn.ubiot.common.autobean.dao.GetTablesDao;
import cn.ubiot.common.autobean.utils.ConfigUtil;
import cn.ubiot.common.autobean.utils.DataTypeUtil;
import cn.ubiot.common.autobean.utils.FileUtil;
import cn.ubiot.common.autobean.utils.NameUtil;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 生成Bean实体类的dao层实现类
 *
 * @author
 */
public class BeanAutoDaoImpl implements BeanAutoDao {

    //从GetTablesDaoImpl中获得装有所有表结构的List
    GetTablesDao getTables = new GetTablesDaoImpl();
    List<TableStruct> list = getTables.getTablesStruct();

    //通过表名、字段名称、字段类型创建Bean实体
    @Override
    public boolean createBean() {
        //获得配置文件的参数
        //项目路径
//        String projectPath = ConfigUtil.projectPath;
        String projectPath = System.getProperty("user.dir");
        //是否生成实体类
        String beanFalg = ConfigUtil.beanFlag;
        //Bean实体类的包名
        String beanPackage = ConfigUtil.beanPackage;
        //Bean实体类继承的类
        String beanExtends = ConfigUtil.beanExtends;
        //Bean继承的类的属性
        String beanExtendsColumn = ConfigUtil.beanExtendsColumn;
        String beanClass = null;
        if(StringUtils.isNotBlank(beanExtends)) {
            beanClass = beanExtends.substring(beanExtends.lastIndexOf(".") + 1);
        }
        //判断是否生成实体类
        if ("true".equals(beanFalg)) {
            //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
            String beanPath = beanPackage.replace(".", "/");
            //Bean实体类的路径
            String path = projectPath + "/src/main/java/" + beanPath;
            //遍历装有所有表结构的List
            for (int i = 0; i < list.size(); i++) {
                //文件名
                String fileName = NameUtil.fileName(list.get(i).getTableName());
                //获得每个表的所有列结构
                List<ColumnStruct> columns = list.get(i).getColumns();
                //(实体类）文件内容
                String packageCon = "package " + beanPackage + ";\n\n";
                StringBuffer importCon = new StringBuffer();
                String className = null;
                if(StringUtils.isNotBlank(beanClass)){
                    className = "public class " + fileName + " extends "+beanClass+"{\n\n";
                    importCon.append("import "+beanExtends+";\n");
                }else{
                    className = "public class " + fileName + "{\n\n";
                }
                StringBuffer classCon = new StringBuffer();
                StringBuffer gettersCon = new StringBuffer();
                StringBuffer settersCon = new StringBuffer();
                StringBuffer noneConstructor = new StringBuffer();
                StringBuffer constructor = new StringBuffer();
                String constructorParam = "";
                StringBuffer constructorCon = new StringBuffer();
                //遍历List，将字段名称和字段类型写进文件
                for (int j = 0; j < columns.size(); j++) {
                    //变量名（属性名）
                    String columnName = NameUtil.columnName(columns.get(j).getColumnName());

                    //不生成继承类中的属性
                    if(StringUtils.isNotBlank(beanExtendsColumn)
                        &&beanExtendsColumn.contains(columnName)){
                        continue;
                    }

                    //获得数据类型
                    String type = columns.get(j).getDataType();
                    //将mysql数据类型转换为java数据类型
                    String dateType = DataTypeUtil.getType(type);
                    //有date类型的数据需导包
                    if ("Date".equals(dateType)) {
                        importCon.append("import java.util.Date;\n\n");
                        if(importCon.indexOf("java.util.Date")== -1) {
                            importCon.append("import java.util.Date;\n");
                        }
                    }
                    //有Timestamp类型的数据需导包
                    if ("Timestamp".equals(dateType)) {
//                        importCon.append("import java.sql.Timestamp;\n\n");
                        dateType = "Date";
                        if(importCon.indexOf("java.util.Date")== -1) {
                            importCon.append("import java.util.Date;\n");
                        }
                    }

                    //生成属性
                    classCon.append("\t" + "private " + dateType + " " + columnName + ";\n\n");
                    //get、set的方法名
                    String getSetName = columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    //生成get方法
                    gettersCon.append("\t" + "public " + dateType + " get" + getSetName + "(){\n" +
                            "\t\t" + "return " + columnName + ";\n" +
                            "\t" + "}\n");
                    //生成set方法
                    settersCon.append("\t" + "public void set" + getSetName + "(" + dateType + " " + columnName + "){\n" +
                            "\t\t" + "this." + columnName + " = " + columnName + ";\n" +
                            "\t" + "}\n");
                    //获得有参构造器参数
                    if (constructorParam == null || "".equals(constructorParam)) {
                        constructorParam = dateType + " " + columnName;
                    } else {
                        constructorParam += "," + dateType + " " + columnName;
                    }
                    //获得有参构造器的内容
                    constructorCon.append("\t\t" + "this." + columnName + " = " + columnName + ";\n");
                }
                //生成无参构造器
                noneConstructor.append("\t" + "public " + fileName + "(){\n" +
                        "\t\t" + "super();\n" +
                        "\t" + "}\n\n");
                //生成有参构造
                constructor.append("\t" + "public " + fileName + "(" + constructorParam + "){\n" +
                        "\t\t" + "super();\n" + constructorCon +
                        "\t" + "}\n\n");
                //拼接(实体类）文件内容
                StringBuffer content = new StringBuffer();
                content.append(packageCon);
                content.append(importCon.toString());
                content.append(className);
                content.append(classCon.toString()+"\n");
                content.append(noneConstructor.toString());
                content.append(constructor.toString());
                content.append(gettersCon.toString());
                content.append(settersCon.toString());
                content.append("}");
                FileUtil.createFileAtPath(path + "/", fileName + ".java", content.toString());
            }
            return true;
        }
        return false;
    }

}
