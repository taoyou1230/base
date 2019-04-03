package cn.ubiot.common.autobean.dao.impl;

import cn.ubiot.common.autobean.bean.TableStruct;
import cn.ubiot.common.autobean.dao.DaoAutoDao;
import cn.ubiot.common.autobean.dao.GetTablesDao;
import cn.ubiot.common.autobean.utils.ConfigUtil;
import cn.ubiot.common.autobean.utils.FileUtil;
import cn.ubiot.common.autobean.utils.NameUtil;
import cn.ubiot.utils.AnnotationUtils;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;

/**
 * 生成Dao接口的dao层实现类
 * @author
 *
 */
public class DaoAutoDaoImpl implements DaoAutoDao {

    //从GetTablesDaoImpl中获得装有所有表结构的List
    GetTablesDao getTables = new GetTablesDaoImpl();
    List<TableStruct> list = getTables.getTablesStruct();
    //获得配置文件的参数
    //项目路径
    String projectPath = System.getProperty("user.dir");
    //是否生成Dao
    String daoFalg= ConfigUtil.daoFlag;
    //Dao接口的包名
    String daoPackage= ConfigUtil.daoPackage;
    //Bean实体类的包名
    String beanPackage= ConfigUtil.beanPackage;
    //Dao接口继承的类
    String daoExtends = ConfigUtil.daoExtends;
    String daoClass = null;
    //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
    String daoPath=daoPackage.replace(".", "/");
    String beanPath=beanPackage.replace(".", "/");
    //Dao接口的路径
    String path =projectPath+"/src/main/java/"+daoPath;
    {
        if (StringUtils.isNotBlank(daoExtends)) {
            daoClass = daoExtends.substring(daoExtends.lastIndexOf(".") + 1);
        }
    }
    //通过表名、字段名称、字段类型创建Dao接口
    @Override
    public boolean createDao() {
        if("true".equals(daoFalg) ){
            //遍历装有所有表结构的List
            for (int i = 0; i < list.size(); i++) {
                //文件名
                String fileName= NameUtil.fileName(list.get(i).getTableName())+"Mapper";
                String beanName = NameUtil.fileName(list.get(i).getTableName());
                this.createDaoFile(fileName,beanName);
            }
            return true;
        }
        return false;
    }

    //通过表名、字段名称、字段类型创建Dao接口
    @Override
    public boolean createDaoByEntity() throws ClassNotFoundException {
        if("true".equals(daoFalg) ){
            //路径下的文件
            List<File> entityList = FileUtil.getFiles(projectPath+"/src/main/java/"+beanPath);
            for(File file:entityList){
                //Dao接口的路径
                String path =projectPath+"/src/main/java/"+daoPath;
                //文件名
                String beanName = file.getName().substring(0,file.getName().lastIndexOf("."));
                String fileName=beanName+"Mapper";
                //判断是否需要创建
                AnnotationUtils annotationUtils = new AnnotationUtils();
                Table table = (Table)annotationUtils.getClassAnnotation(beanPackage+"."+beanName, Table.class);
                if(table==null){
                    continue;
                }
                this.createDaoFile(fileName,beanName);
            }
            return true;
        }
        return false;
    }

    /**
     * 生成dao接口文件
     * @param fileName
     * @param beanName
     */
    private void createDaoFile(String fileName,String beanName){
        //(Dao接口）文件内容
        String packageCon ="package "+daoPackage+";\n\n";
        StringBuffer importCon=new StringBuffer();
        importCon.append("import org.apache.ibatis.annotations.Mapper;\n");
        String className = null;
        if(StringUtils.isNotBlank(daoClass)){
            className = "@Mapper\npublic interface " + fileName + " extends "+daoClass+"<"+beanName+">{\n\n";
            importCon.append("import "+daoExtends+";\n");
        }else{
            className = "@Mapper\npublic interface " + fileName + "{\n\n";
        }
        StringBuffer classCon = new StringBuffer();

        //生成导包内容
        importCon.append("import"+"\t"+beanPackage+"."+beanName+";\n\n");
        importCon.append("import java.util.List;\n\n");
        importCon.append("import java.util.Map;\n\n");
        //生成接口方法
        classCon.append("\t/**\n"
                +"\t* 根据条件查询数据\n"
                +"\t* @param map = {\n"
                +"\t*  filters:查询条件，\n"
                +"\t*  sortInfo：排序条件\n"
                +"\t* }\n"
                +"\t* @return\n"
                +"\t*/\n"
                +"\t"+"public List<"+beanName+"> queryByCondition(Map<String,Object>map);//添加一条完整记录\n\n");

        //拼接(Dao接口）文件内容
        StringBuffer content=new StringBuffer();
        content.append(packageCon);
        content.append(importCon.toString());
        content.append(className);
        content.append(classCon.toString());
        content.append("\n}");
        FileUtil.createFileAtPath(path+"/", fileName+".java", content.toString());
    }
}
