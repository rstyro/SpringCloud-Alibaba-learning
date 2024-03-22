package top.lrshuai.common.security.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import top.lrshuai.common.core.utils.InterceptorUtil;
import top.lrshuai.common.security.annotation.LoginIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验拦截
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            //HandlerMethod 封装方法定义相关的信息,如类,方法,参数等
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法中是否包含注解
            LoginIgnore loginIgnore = InterceptorUtil.getAnnotation(handlerMethod,LoginIgnore.class);
            if(loginIgnore != null){
                // 免登录接口放行
                return true;
            }

        }
        return true;
    }


}
