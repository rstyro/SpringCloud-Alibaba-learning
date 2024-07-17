package top.lrshuai.common.core.utils;

import org.springframework.core.MethodParameter;

import java.lang.annotation.Annotation;

public class AnnotationUtil {
    /**
     * 判断方法或类上有没有注解
     * @param method mothod对象
     * @param annotations 注解类数组
     * @param <A> Annotation类型的class
     * @return boolean
     */
    public static  <A extends Annotation> boolean hasMethodAnnotation(MethodParameter method, Class<A>[] annotations){
        if(annotations != null){
            for(Class<A> annotation:annotations){
                if(method.hasMethodAnnotation(annotation) || method.getDeclaringClass().isAnnotationPresent(annotation)){
                    return true;
                }
            }
        }
        return false;
    }
}
