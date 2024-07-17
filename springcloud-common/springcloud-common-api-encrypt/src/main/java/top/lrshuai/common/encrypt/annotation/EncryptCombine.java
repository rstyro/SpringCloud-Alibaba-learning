package top.lrshuai.common.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 组合注解，接收解密，返回加密
 * @author rstyro
 */
@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@DecodeRequestBody
@EncodeResponseBody
public @interface EncryptCombine {
}
