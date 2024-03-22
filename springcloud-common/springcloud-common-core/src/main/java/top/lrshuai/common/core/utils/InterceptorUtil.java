package top.lrshuai.common.core.utils;

import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 拦截器工具类
 */
public class InterceptorUtil {

    /**
     * 从方法或者类中获取注解
     * @param handlerMethod @RequestMapping标记的方法对应的handler
     * @param t 注解类
     * @return 注解
     */
    public static  <T extends Annotation> T getAnnotation(HandlerMethod handlerMethod, Class<T> t){
        Method method = handlerMethod.getMethod();
        // 获取方法中是否包含注解
        T annotation = method.getAnnotation(t);
        if(annotation==null){
            //获取 类中是否包含注解，也就是controller
            annotation=method.getDeclaringClass().getAnnotation(t);
        }
        return annotation;
    }
}
