package top.lrshuai.common.encrypt.advice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import top.lrshuai.common.core.utils.AnnotationUtil;
import top.lrshuai.common.encrypt.annotation.DecodeRequestBody;
import top.lrshuai.common.encrypt.annotation.EncryptCombine;
import top.lrshuai.common.encrypt.config.EncryptProperties;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 请求参数到controller之前的处理
 * @author rstyro
 */
@ConditionalOnProperty(name = "api.encrypt.enable", havingValue = "true")
@ControllerAdvice
public class EncryptRequestAdvice implements RequestBodyAdvice {

    @Resource
    private EncryptProperties encryptConfig;

    /**
     * 是否需要解码
     */
    private boolean isDecode;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        // 方法或类上有注解
        if (AnnotationUtil.hasMethodAnnotation(methodParameter,new Class[]{EncryptCombine.class, DecodeRequestBody.class})) {
            isDecode=true;
            // 这里返回true 才支持
            return true;
        }
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        if(isDecode){
            return new DecodeInputMessage(httpInputMessage, encryptConfig);
        }
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object obj, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        // 这里就是已经读取到body了，obj就是
        return obj;
    }

    @Override
    public Object handleEmptyBody(Object obj, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        // body 为空的时候调用
        return obj;
    }

}
