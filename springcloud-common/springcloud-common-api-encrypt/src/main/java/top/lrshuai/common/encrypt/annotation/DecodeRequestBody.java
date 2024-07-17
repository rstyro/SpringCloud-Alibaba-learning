package top.lrshuai.common.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 接收参数是否需要解密
 * @author rstyro
 */
@Documented
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DecodeRequestBody {
}
