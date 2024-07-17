package top.lrshuai.common.encrypt.advice;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.utils.AnnotationUtil;
import top.lrshuai.common.encrypt.annotation.EncodeResponseBody;
import top.lrshuai.common.encrypt.annotation.EncryptCombine;
import top.lrshuai.common.encrypt.config.EncryptProperties;

import javax.annotation.Resource;

/**
 * 接口返回对象加密
 *
 * @author rstyro
 */
@Slf4j
@ControllerAdvice
@ConditionalOnProperty(name = "api.encrypt.enable", havingValue = "true")
public class EncryptResponseAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private EncryptProperties encryptProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // return true 有效
        return true;
    }

    /**
     * 返回结果加密
     *
     * @param obj                接口返回的对象
     * @param methodParameter    method
     * @param mediaType          mediaType
     * @param aClass             HttpMessageConverter class
     * @param serverHttpRequest  request
     * @param serverHttpResponse response
     * @return obj
     */
    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        // 方法或类上有注解
        if (AnnotationUtil.hasMethodAnnotation(methodParameter, new Class[]{EncryptCombine.class, EncodeResponseBody.class})) {
            // 这里假设已经定义好返回的model就是Result
            if (obj instanceof R) {
                try {
                    // 1、随机aes密钥
                    String randomAesKey = EncryptProperties.generateAesSecret(256);
                    // 2、数据体
                    Object data = ((R) obj).getData();
                    // 3、转json字符串
                    String jsonString = JSON.toJSONString(data);
                    AES aes = encryptProperties.getAes(randomAesKey);
                    // 4、aes加密数据体
                    String aesEncode = aes.encryptBase64(jsonString);
                    // 5、重新设置数据体
                    ((R) obj).data(aesEncode);
                    String encodeAesKey = SecureUtil.rsa(null,encryptProperties.getRsaProperties().getWebPublicKey())
                            .encryptBase64(randomAesKey, KeyType.PublicKey);
                    // 6、使用前端的rsa公钥加密 aes密钥 返回给前端
                    ((R) obj).addExtend(encryptProperties.getAesProperties().getKeyName(), encodeAesKey);
                    // 7、返回
                    return obj;
                } catch (Exception e) {
                    log.error("加密失败：", e);
                }
            }
        }
        return obj;
    }


}
