package cn.ubiot.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {

    /**
     * 获取类的注解
     * @param className
     * @param annotationClass
     * @return Annotation
     * @throws ClassNotFoundException
     */
    public Annotation getClassAnnotation(String className,Class annotationClass) throws ClassNotFoundException{
        Class<?> clazz = Class.forName(className);//静态加载类
        boolean isNotEmpty = clazz.isAnnotationPresent(annotationClass);//判断stu是不是使用了我们刚才定义的注解接口
        if(isNotEmpty) {
            Annotation[] annotation = clazz.getAnnotations();//获取注解接口中的
            for (Annotation a : annotation) {
                if (a.annotationType().equals(annotationClass)) {
                    return a;
                }
            }
        }
        return null;
    }

    /**
     * 获取方法上的注解
     * @param className
     * @param annotationClass
     * @return List<Annotation>
     * @throws ClassNotFoundException
     */
    public Map<String,Annotation> getMethodAnnotation(String className, Class annotationClass) throws ClassNotFoundException{
        Map<String,Annotation> map = new HashMap<String,Annotation>();
        Class<?> clazz = Class.forName(className);//静态加载类
        Method[] method = clazz.getMethods();//
        for(Method m:method){
            boolean isNotEmpty = m.isAnnotationPresent(annotationClass);
            if(isNotEmpty){
                Annotation[] annotations = m.getAnnotations();
                for(Annotation a:annotations){
                    if (a.annotationType().equals(annotationClass)) {
                        map.put(m.getName(),a);
                        break;
                    }
                }
            }
        }
        return map;
    }

    /**
     * 获取属性上的注解
     * @param className
     * @param annotationClass
     * @throws ClassNotFoundException
     */
    public Map<String,Annotation> getFiledAnnotation(String className, Class annotationClass) throws ClassNotFoundException{
        Map<String,Annotation> map = new HashMap<String,Annotation>();
        Class<?> clazz = Class.forName(className);//静态加载类
        //get Fields by force
        Field[] field = clazz.getDeclaredFields();
        for(Field f:field){
            boolean isNotEmpty = f.isAnnotationPresent(annotationClass);
            if(isNotEmpty){
                Annotation[] aa = f.getAnnotations();
                for(Annotation a:aa){
                    if(a.annotationType().equals(annotationClass)){
                        map.put(f.getName(),a);
                        break;
                    }
                }
            }
            f.setAccessible(true);
        }
        return map;
    }

}
