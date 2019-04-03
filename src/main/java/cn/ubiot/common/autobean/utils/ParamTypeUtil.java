package cn.ubiot.common.autobean.utils;

/**
 * mybatis的parameterType处理工具类
 * @author
 *
 */
public class ParamTypeUtil {



    public static String getParamType(String dataType){
        String type="";
        if("String".equals(dataType)){
            type="java.lang.String";
        }
        if("BigDecimal".equals(dataType)){
            type="java.math.BigDecimal";
        }
        if("boolean".equals(dataType)){
            type="java.lang.Boolean";
        }
        if("byte".equals(dataType)){
            type="java.lang.Byte";
        }
        if("short".equals(dataType)){
            type="java.lang.Short";
        }
        if("int".equals(dataType)){
            type="java.lang.Integer";
        }
        if("Integer".equals(dataType)){
            type="java.lang.Integer";
        }
        if("long".equals(dataType)){
            type="java.lang.Long";
        }
        if("float".equals(dataType)){
            type="java.lang.Double";
        }
        if("double".equals(dataType)){
            type="java.lang.Double";
        }
        if("Date".equals(dataType)){
            type="java.util.Date";
        }
        if("Time".equals(dataType)){
            type="java.sql.Time";
        }
        if("Timestamp".equals(dataType)){
            type="java.sql.Timestamp";
        }
        if("List".equals(dataType)){
            type="java.util.List";
        }
        if("Map".equals(dataType)){
            type="java.util.Map";
        }
        return type;
    }
}
