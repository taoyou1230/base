package cn.ubiot.common.autobean.dao.impl;

import cn.ubiot.common.autobean.bean.TableStruct;
import cn.ubiot.common.autobean.dao.GetTablesDao;
import cn.ubiot.common.autobean.dao.ServiceAutoDao;
import cn.ubiot.common.autobean.utils.ConfigUtil;
import cn.ubiot.common.autobean.utils.FileUtil;
import cn.ubiot.common.autobean.utils.NameUtil;
import cn.ubiot.utils.AnnotationUtils;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.List;

/**
 * 生成Service接口的dao层实现类
 * @author
 *
 */
public class ServiceAutoDaoImpl implements ServiceAutoDao {

    //从GetTablesDaoImpl中获得装有所有表结构的List
    GetTablesDao getTables = new GetTablesDaoImpl();
    List<TableStruct> list = getTables.getTablesStruct();
    //获得配置文件的参数
    //项目路径
    String projectPath = System.getProperty("user.dir");
    //是否生成Service
    String serviceFalg= ConfigUtil.serviceFlag;
    //Service接口的包名
    String servicePackage= ConfigUtil.servicePackage;
    //Bean实体类的包名
    String beanPackage=ConfigUtil.beanPackage;
    //Service接口继承的类
    String serviceExtends = ConfigUtil.serviceExtends;
    String serviceClass = null;
    //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
    String servicePath=servicePackage.replace(".", "/");
    String beanPath=beanPackage.replace(".", "/");
    //Service接口的路径
    String path =projectPath+"/src/main/java/"+servicePath;

    {
        if (StringUtils.isNotBlank(serviceExtends)) {
            serviceClass = serviceExtends.substring(serviceExtends.lastIndexOf(".") + 1);
        }
    }

    //通过表名、字段名称、字段类型创建Service接口
    @Override
    public boolean createService() {
        if("true".equals(serviceFalg) ){
            //遍历装有所有表结构的List
            for (int i = 0; i < list.size(); i++) {
                //文件名
                String fileName= NameUtil.fileName(list.get(i).getTableName())+"Service";
                String beanName = NameUtil.fileName(list.get(i).getTableName());
                //(Service接口）文件内容
                this.createServiceFile(fileName,beanName);
            }
            return true;
        }
        return false;
    }

    /**
     * 通过实体类创建Service接口
     *
     * @return
     */
    @Override
    public boolean createServiceByEntity() throws ClassNotFoundException {
        if("true".equals(serviceFalg) ){
            //路径下的文件
            List<File> entityList = FileUtil.getFiles(projectPath+"/src/main/java/"+beanPath);
            //遍历所有实体类文件
            for(File file:entityList){
                //文件名
                String beanName = file.getName().substring(0,file.getName().lastIndexOf("."));
                String fileName=beanName+"Service";
                //判断是否需要创建
                AnnotationUtils annotationUtils = new AnnotationUtils();
                Table table = (Table)annotationUtils.getClassAnnotation(beanPackage+"."+beanName, Table.class);
                if(table==null){
                    continue;
                }
                //(Service接口）文件内容
                this.createServiceFile(fileName,beanName);
            }
            return true;
        }
        return false;
    }


    private void createServiceFile(String fileName,String beanName){
        String packageCon ="package "+servicePackage+";\n\n";
        StringBuffer importCon=new StringBuffer();
        String className = null;
        if(StringUtils.isNotBlank(serviceClass)){
            className = "public interface " + fileName + " extends "+serviceClass+"<"+beanName+">{\n\n";
            importCon.append("import "+serviceExtends+";\n");
        }else{
            className = "public interface " + fileName + "{\n\n";
        }
        StringBuffer classCon = new StringBuffer();
        //生成导包内容
        importCon.append("import"+" "+beanPackage+"."+beanName+";\n");

//                importCon.append("import java.util.List;\n\n");
//
//                //生成接口方法
//                classCon.append("\t"+"public int insertRecord("+beanName+" record);//添加一条完整记录\n\n");
//                classCon.append("\t"+"public int insertSelective("+beanName+" record);//添加指定列的数据\n\n");
//                classCon.append("\t"+"public int deleteById("+dateType+" "+columnName+");//通过Id(主键)删除一条记录\n\n");
//                classCon.append("\t"+"public int updateByIdSelective("+beanName+" record);//按Id(主键)修改指定列的值\n\n");
//                classCon.append("\t"+"public int updateById("+beanName+" record);//按Id(主键)修改指定列的值\n\n");
//                classCon.append("\t"+"public int countRecord();//计算表中的总记录数\n\n");
//                classCon.append("\t"+"public int countSelective("+beanName+" record);//根据条件计算记录条数\n\n");
//                classCon.append("\t"+"public int maxId();//获得表中的最大Id\n\n");
//                classCon.append("\t"+"public"+"\t"+beanName+"\t"+"selectById("+dateType+"\t"+columnName+");//通过Id(主键)查询一条记录\n\n");
//                classCon.append("\t"+"public List selectAll();//查询所有记录\n\n");

        //拼接(Service接口）文件内容
        StringBuffer content=new StringBuffer();
        content.append(packageCon);
        content.append(importCon.toString());
        content.append(className);
        content.append(classCon.toString());
        content.append("\n}");
        FileUtil.createFileAtPath(path+"/", fileName+".java", content.toString());
    }
}