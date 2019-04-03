package cn.ubiot.common.autobean.dao.impl;

import cn.ubiot.common.autobean.bean.ColumnStruct;
import cn.ubiot.common.autobean.bean.TableStruct;
import cn.ubiot.common.autobean.dao.GetTablesDao;
import cn.ubiot.common.autobean.dao.MapperXmlAutoDao;
import cn.ubiot.common.autobean.utils.*;
import cn.ubiot.utils.AnnotationUtils;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 生成Mapper.xml的dao层实现类
 * @author
 *
 */
public class MapperXmlAutoDaoImpl implements MapperXmlAutoDao {

    //从GetTablesDaoImpl中获得装有所有表结构的List
    GetTablesDao getTables = new GetTablesDaoImpl();
    List<TableStruct> list = getTables.getTablesStruct();
    //获得配置文件的参数
    //项目路径
    String projectPath = System.getProperty("user.dir");
    //是否生成Mapper.xml
    String mapperXmlFalg= ConfigUtil.mapperXmlFlag;
    //Mapper.xml的包名
    String mapperXmlPackage=ConfigUtil.mapperXmlPackage;
    //Bean实体类的包名
    String beanPackage=ConfigUtil.beanPackage;
    //Dao接口的包名
    String daoPackage= ConfigUtil.daoPackage;
    //将包名com.xxx.xxx形式，替换成com/xxx/xxx形成
    String mapperXmlPath=mapperXmlPackage.replace(".", "/");
    String beanPath=beanPackage.replace(".", "/");
    //Mapper.xml的路径
    String path =projectPath+"/src/main/resources/"+mapperXmlPath;

    //通过表名、字段名称、字段类型创建Mapper.xml
    @Override
    public boolean createMapperXml() {
        if("true".equals(mapperXmlFalg) ){
            //遍历装有所有表结构的List
            for (int i = 0; i < list.size(); i++) {
                //表名
                String tableName =list.get(i).getTableName();

                //文件名
                String fileName= NameUtil.fileName(tableName)+"Mapper";
                String beanName = NameUtil.fileName(tableName);
                String daoName = NameUtil.fileName(tableName)+"Mapper";

                //获得每个表的所有列结构
                List<ColumnStruct> columns =list.get(i).getColumns();

                //(Mapper.xml）文件内容
                this.createMapperXmlFile(fileName,daoName,beanName,tableName,columns);
            }
            return true;
        }
        return false;
    }


    /**
     * 通过实体类创建Mapper.xml
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public boolean createMapperXmlByEntity() throws ClassNotFoundException {
        if("true".equals(mapperXmlFalg) ){

            //路径下的文件
            List<File> entityList = FileUtil.getFiles(projectPath+"/src/main/java/"+beanPath);
            //遍历所有实体类文件
            for(File file:entityList){
                //文件名
                String beanName = file.getName().substring(0,file.getName().lastIndexOf("."));
                String fileName=beanName+"Mapper";
                String daoName =beanName+"Mapper";
                //判断是否需要创建
                AnnotationUtils annotationUtils = new AnnotationUtils();
                Table table = (Table)annotationUtils.getClassAnnotation(beanPackage+"."+beanName, Table.class);
                if(table==null){
                    continue;
                }
                //表名
                String tableName =table.name();
                //获取实体类属性
                List<ColumnStruct> columns = new ArrayList<ColumnStruct>();
                //获得每个表的所有列结构
                Map<String, Annotation> map = annotationUtils.getFiledAnnotation(beanPackage+"."+beanName, Column.class);
                for(String key:map.keySet()){
                    ColumnStruct column = new ColumnStruct();
                    Column colu = ((Column)map.get(key));
                    column.setColumnName(colu.name());
                    column.setDataType(colu.type());
                    column.setColumnKey(colu.isKey()?"PRI":"");
                    columns.add(column);
                }
                //(Mapper.xml）文件内容
                this.createMapperXmlFile(fileName,daoName,beanName,tableName,columns);

            }
            return true;
        }
        return false;
    }

    /**
     * 创建mapper.xml文件
     * @param fileName 文件名
     * @param daoName 对应的dao接口名
     * @param beanName 实体类名称
     * @param tableName 表名
     * @param columns 字段集合
     */
    private void createMapperXmlFile(String fileName,String daoName,String beanName,String tableName,List<ColumnStruct>columns){
        StringBuffer headCon = new StringBuffer();
        headCon.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        headCon.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        headCon.append("<mapper namespace=\""+daoPackage+"."+daoName+"\">\n");

        StringBuffer resultMapCon = new StringBuffer();
        resultMapCon.append("\t"+"<resultMap id=\"BaseResultMap\" type=\""+beanPackage+"."+beanName+"\">\n");

        StringBuffer baseColCon = new StringBuffer();
        baseColCon.append("\t"+"<sql id=\"Base_Column_List\">\n");

        StringBuffer sortCondition = new StringBuffer();
        sortCondition.append("\t<sql id=\"Sort_Condition\">\n");
        sortCondition.append("\t\t<trim suffixOverrides=\",\">\n");
        sortCondition.append("\t\torder by\n");
        sortCondition.append("\t\t<choose>\n");
        sortCondition.append("\t\t\t<when test=\"sortInfo != null and sortInfo.size>0\">\n");

        StringBuffer queryCondition = new StringBuffer();
        queryCondition.append("\t<sql id=\"Query_Condition\">\n");
        queryCondition.append("\t\t1=1\n");
        queryCondition.append("\t\t<if test=\"filters !=null and filters.size>0\">\n");

        StringBuffer queryByCondition = new StringBuffer();
        queryByCondition.append("\t"+"<select id=\"queryByCondition\" resultMap=\"BaseResultMap\" parameterType=\"java.util.Map\">\n");
        queryByCondition.append("\t\tselect <include refid=\"Base_Column_List\"/> from "+tableName+"\n");
        queryByCondition.append("\t\t"+"<where>\n");
        queryByCondition.append("\t\t\t"+"<include refid=\"Query_Condition\"/>\n");
        queryByCondition.append("\t\t"+"</where>\n");
        queryByCondition.append("\t\t"+"<include refid=\"Sort_Condition\"/>\n");
        queryByCondition.append("\t</select>\n");

        StringBuffer resultMapColum = new StringBuffer();
        //遍历List，将字段名称和字段类型、属性名写进文件
        for (int j = 0; j <columns.size(); j++) {
            //字段名
            String columnName =columns.get(j).getColumnName();
            //属性（变量）名
            String attrName = NameUtil.columnName(columns.get(j).getColumnName());
            //字段类型
            String type= DataTypeUtil.getType(columns.get(j).getDataType());;
            String jdbcType = JdbcTypeUtil.getJdbcType(type);
            if("INT".equalsIgnoreCase(jdbcType)){
                jdbcType="INTEGER";
            }
            if("PRI".equalsIgnoreCase(columns.get(j).getColumnKey())){
                resultMapColum = new StringBuffer("\t\t"+"<id column=\""+columnName+"\" property=\""+attrName+"\" jdbcType=\""+jdbcType+"\"/>\n"
                        +resultMapColum.toString());
            }else{
                resultMapColum.append("\t\t"+"<result column=\""+columnName+"\" property=\""+attrName+"\" jdbcType=\""+jdbcType+"\"/>\n");
            }
            if(j==0) {
                baseColCon.append("\t\t" + columnName);
            }else{
                baseColCon.append(","+columnName);
            }
            sortCondition.append("\t\t\t\t<if test=\"sortInfo."+attrName+" =='desc'\">\n");
            sortCondition.append("\t\t\t\t\t"+columnName+" desc,\n");
            sortCondition.append("\t\t\t\t</if>\n");
            sortCondition.append("\t\t\t\t<if test=\"sortInfo."+attrName+" =='asc'\">\n");
            sortCondition.append("\t\t\t\t\t"+columnName+" asc,\n");
            sortCondition.append("\t\t\t\t</if>\n");

            if("INTEGER".equalsIgnoreCase(jdbcType)){
                queryCondition.append("\t\t\t<if test=\"filters."+attrName+" !=null and filters."+attrName+" !=0\">\n");
            }else if("VARCHAR".equalsIgnoreCase(jdbcType)){
                queryCondition.append("\t\t\t<if test=\"filters."+attrName+" !=null and filters."+attrName+" !=''\">\n");
            }else{
                queryCondition.append("\t\t\t<if test=\"filters."+attrName+" !=null\">\n");
            }

            queryCondition.append("\t\t\t\tand "+columnName+" = #{filters."+attrName+"}\n");
            queryCondition.append("\t\t\t</if>\n");
        }
        resultMapCon.append(resultMapColum);
        resultMapCon.append("\t"+"</resultMap>\n\n");
        baseColCon.append("\n\t"+"</sql>\n\n");
        sortCondition.append("\t\t\t</when>\n\t\t\t<otherwise>\n\t\t\t\tcreated_at desc\n\t\t\t</otherwise>\n\t\t</choose>\n\t\t</trim>\n\t</sql>\n\n");
        queryCondition.append("\t\t</if>\n\t</sql>\n\n");

        //拼接(Mapper.xml）文件内容
        StringBuffer content=new StringBuffer();
        content.append(headCon);
        content.append(resultMapCon);
        content.append(baseColCon);
        content.append(sortCondition);
        content.append(queryCondition);
        content.append(queryByCondition);
        content.append("</mapper>");

        FileUtil.createFileAtPath(path+"/", fileName+".xml", content.toString());
    }
}
