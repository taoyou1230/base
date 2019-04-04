package cn.ubiot.utils;

import cn.ubiot.common.user.entity.User;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 排序工具类
 */
public class SortUtils {

    //升序
    private static final String SORT_ASC = "asc";
    //降序
    private static final String SORT_DESC = "desc";

    /**
     * 根据集合中的Map的key排序
     * @param list List<Map<String,Object>
     * @param key map内的key名
     * @param sort asc 升序，desc 降序
     */
    public static void sortListMap(List<Map<String,Object>> list,String key,String sort){
        Collections.sort(list,(o1, o2) -> {
            int ret = 0;
            if(o1.get(key)instanceof Number) {
                ret = new Double(o1.get(key).toString()).compareTo(new Double(o2.get(key).toString()));
            }else if(o1.get(key)instanceof Date){
                ret = ((Date)o1.get(key)).compareTo((Date) o2.get(key));
            }else  {
                ret = String.valueOf(o1.get(key)).compareTo(String.valueOf(o2.get(key)));
            }
            if(null != sort && SORT_DESC.equalsIgnoreCase(sort)){
                return -ret;
            }else{
                return ret;
            }
        });
    }


    /**
     * 对List集合排序
     * @param list 源数据 排序集合
     * @param sort 升序asc 还是 降序desc，默认升序
     * @return List
     */
    public static List<?> sortList(List<?> list,String sort){
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                int ret = 0;
                if(o1 instanceof Number){
                    ret = (new Double(o1.toString())).compareTo(new Double(o2.toString()));
                } else if(o1 instanceof Date){
                    ret = ((Date)o1).compareTo((Date) o2);
                } else {
                    ret = String.valueOf(o1).compareTo(String.valueOf(o2));
                }
                if(null != sort && SORT_DESC.equalsIgnoreCase(sort)){
                    return -ret;
                }else{
                    return ret;
                }
            }
        });
        return list;
    }


    /**
     * List 泛型 排序
     * @param list 源数据 排序集合
     * @param field 排序的数据字段名称
     * @param sort 升序asc 还是 降序desc，默认升序
     * @param <T> 泛型T
     * @return List
     */
    public static <T> void sortListObject(List<T> list,final String field,final String sort){
        Collections.sort(list, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return SortUtils.compare(o1,o2,field,sort);
            }
        });
    }

    /**
     * List 泛型 排序
     * @param list 源数据 排序集合
     * @param fields 排序的数据字段名称
     * @param sorts 升序asc 还是 降序desc,默认升序
     * @param <T> 泛型T
     * @return List
     */
    public static <T> void sortListObject(List<T> list,final String [] fields,final String [] sorts){
        if(null != fields && fields.length > 0){
            for(int index = 0;index < fields.length;index ++){
                String sortRule = SORT_ASC;
                if(null != sorts && sorts.length >= index && null != sorts[index]){
                    sortRule = sorts[index];
                }
                final String sort = sortRule;
                final String field = fields[index];
                Collections.sort(list, new Comparator<T>() {
                    @Override
                    public int compare(T o1, T o2) {
                        return SortUtils.compare(o1,o2,field,sort);
                    }
                });
            }
        }
    }

    /**
     * 比较两个对象的属性
     * @param o1 对象1
     * @param o2 对象2
     * @param field 属性名称
     * @param sort 升序asc 降序desc
     * @param <T>
     * @return
     */
    public static <T> int compare(T o1,T o2,String field,String sort){
        int ret = 0;
        try {
            Method method1 = o1.getClass().getDeclaredMethod(getMethodName(field),null);
            Method method2 = o1.getClass().getDeclaredMethod(getMethodName(field), null);
            Field field1 = o1.getClass().getDeclaredField(field);
            field1.setAccessible(true);
            Class<?> type = field1.getType();

            if(type == int.class){
                ret = Integer.compare(field1.getInt(o1), field1.getInt(o2));
            } else if(type == double.class){
                ret = Double.compare(field1.getDouble(o1), field1.getDouble(o2));
            } else if(type == long.class){
                ret = Long.compare(field1.getLong(o1), field1.getLong(o2));
            } else if(type == float.class){
                ret = Float.compare(field1.getFloat(o1), field1.getFloat(o2));
            } else if(type == Date.class){
                ret = ((Date)field1.get(o1)).compareTo((Date) field1.get(o2));
            } else if(type == String.class){
                ret = String.valueOf(field1.get(o1)).compareTo(String.valueOf(field1.get(o2)));
            } else if(isNumber(String.valueOf(field1.get(o1))) && isNumber(String.valueOf(field1.get(o2)))){
                ret = (new Double(method1.invoke(o1).toString())).compareTo(new Double(method2.invoke(o2).toString()));
            } else {
                ret = String.valueOf(field1.get(o1)).compareTo(String.valueOf(field1.get(o2)));
            }


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if(null != sort && SORT_DESC.equalsIgnoreCase(sort)){
            return -ret;
        }else{
            return ret;
        }
    }

    /**
     * 是否为数字
     * @param str
     * @return
     */
    private static boolean isNumber(String str){
        boolean flag = false;
        if(isInteger(str) || isFloat(str)){
            flag = true;
        }
        return flag;
    }

    /**
     * 判断字符串是否为整数
     * @param str
     * @return
     */
    private static boolean isInteger(String str){
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为浮点数
     * @param str
     * @return
     */
    private static boolean isFloat(String str){
        return str.matches("[\\d]+\\.[\\d]+");
    }

    /**
     * 获取属性名
     * @param str
     * @return
     */
    private static String getMethodName(String str){
        StringBuffer name = new StringBuffer();
        name = name.append("get").append(firstLetterToCapture(str));
        return name.toString();
    }

    /**
     *  将首字符转为小写
     * @param name
     * @return
     */
    private static String firstLetterToCapture(String name){
        char[] arr = name.toCharArray();
        arr[0] -= 32;
        return String.valueOf(arr);
    }

    public static void main(String[] args){
        List<User> list = new ArrayList<User>() ;
        User user1 = new User();
        user1.setUserId(10);
        user1.setInts(10);
        user1.setDoubles1(12.1);
        user1.setDoubles2(12.1);
        user1.setCompanyName("111");
        list.add(user1);
        User user2 = new User();
        user2.setUserId(1);
        user2.setInts(1);
        user2.setDoubles1(11.1);
        user2.setDoubles2(11.1);
        user2.setCompanyName("11");
        list.add(user2);
        User user3 = new User();
        user3.setUserId(5);
        user3.setInts(3);
        user3.setDoubles1(12.5);
        user3.setDoubles2(12.5);
        user3.setCompanyName("5");
        list.add(user3);
        User user4 = new User();
        user4.setUserId(5);
        user4.setInts(4);
        user4.setDoubles1(12.5);
        user4.setDoubles2(12.5);
        user4.setCompanyName("33");
        list.add(user4);

//        SortUtils.sortListObject(list,"ints","desc");
//        System.out.println(JSON.toJSONString(list));
//        SortUtils.sortListObject(list,"userId","desc");
//        System.out.println(JSON.toJSONString(list));
//        SortUtils.sortListObject(list,"doubles1","desc");
//        System.out.println(JSON.toJSONString(list));
        SortUtils.sortListObject(list,"companyName","desc");
        System.out.println(JSON.toJSONString(list));
//        SortUtils.sortListObject(list,"doubles2","desc");
//        System.out.println(JSON.toJSONString(list));

//        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
//
//        Map<String,Object> map1 = new HashMap<String,Object>();
//        map1.put("ints",10);
//        map1.put("integers",new Integer(10));
//        map1.put("doubles",10.5);
//        map1.put("doubles2",new Double(10.5));
//        map1.put("str","555");
//        map1.put("date",DateUtils.stringToDate("2019-04-10","yyyy-MM-dd"));
//        mapList.add(map1);
//        Map<String,Object> map2 = new HashMap<String,Object>();
//        map2.put("ints",1);
//        map2.put("integers",new Integer(1));
//        map2.put("doubles",1.5);
//        map2.put("doubles2",new Double(1.5));
//        map2.put("str","44");
//        map2.put("date",DateUtils.stringToDate("2019-04-01","yyyy-MM-dd"));
//        mapList.add(map2);
//        Map<String,Object> map3 = new HashMap<String,Object>();
//        map3.put("ints",5);
//        map3.put("integers",new Integer(5));
//        map3.put("doubles",5.5);
//        map3.put("doubles2",new Double(5.5));
//        map3.put("str","66");
//        map3.put("date",DateUtils.stringToDate("2019-04-04","yyyy-MM-dd"));
//        mapList.add(map3);
//        Map<String,Object> map4 = new HashMap<String,Object>();
//        map4.put("ints",5);
//        map4.put("integers",new Integer(5));
//        map4.put("doubles",5.5);
//        map4.put("doubles2",new Double(5.5));
//        map4.put("str","55");
//        map4.put("date",DateUtils.stringToDate("2019-04-04","yyyy-MM-dd"));
//        mapList.add(map4);
////        SortUtils.mapSort(mapList,"doubles","desc");
////        System.out.println(JSON.toJSONString(mapList));
//        SortUtils.mapSort(mapList,"date","asc");
//        for(int i=0;i<mapList.size();i++){
//            System.out.println(DateUtils.dateToString((Date)(mapList.get(i).get("date")),"yyyy-MM-dd"));
//        }

    }


}
