package cn.ubiot.common.autobean.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 获得properties配置文件的参数工具类
 * @author
 *
 */
public class ConfigUtil {

    //生成Bean实体类的参数
    public static String beanFlag;
    public static String beanPackage;
    public static String beanExtends;
    public static String beanExtendsColumn;
    //生成Dao接口的参数
    public static String daoFlag;
    public static String daoPackage;
    public static String daoExtends;
    //生成Mapper.xml的参数
    public static String mapperXmlFlag;
    public static String mapperXmlPackage;
    //生成Service接口的参数
    public static String serviceFlag;
    public static String servicePackage;
    public static String serviceExtends;
    //生成ServiceImpl实现类的参数
    public static String serviceImplFlag;
    public static String serviceImplPackage;
    public static String serviceImplExtends;

    //生成Controller类的参数
    public static String controllerFlag;
    public static String controllerPackage;
    public static String controllerExtends;

    //获取配置文件参数并加载驱动
    static{
        try {
            //得到配置文件的流信息
            InputStream in = DataSourceUtil.class.getClassLoader().getResourceAsStream("config.properties");
            //加载properties文件的工具类
            Properties pro = new Properties();
            //工具类去解析配置文件的流信息
            pro.load(in);
            //将文件得到的信息,赋值到全局变量
            beanFlag =pro.getProperty("beanFlag");
            beanPackage = pro.getProperty("beanPackage");
            beanExtends = pro.getProperty("beanExtends");
            beanExtendsColumn = pro.getProperty("beanExtendsColumn");

            daoFlag =pro.getProperty("daoFlag");
            daoPackage = pro.getProperty("daoPackage");
            daoExtends = pro.getProperty("daoExtends");

            mapperXmlFlag =pro.getProperty("mapperXmlFlag");
            mapperXmlPackage = pro.getProperty("mapperXmlPackage");

            serviceFlag = pro.getProperty("serviceFlag");
            servicePackage = pro.getProperty("servicePackage");
            serviceExtends = pro.getProperty("serviceExtends");

            serviceImplFlag = pro.getProperty("serviceImplFlag");
            serviceImplPackage = pro.getProperty("serviceImplPackage");
            serviceImplExtends = pro.getProperty("serviceImplExtends");

            controllerFlag = pro.getProperty("controllerFlag");
            controllerPackage = pro.getProperty("controllerPackage");
            controllerExtends = pro.getProperty("controllerExtends");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
