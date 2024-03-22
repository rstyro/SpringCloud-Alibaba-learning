package top.lrshuai.common.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在不需要登录验证的Controller的方法或类上使用此注解
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})// 可用在方法名或类上
@Retention(RetentionPolicy.RUNTIME)// 运行时有效
public @interface LoginIgnore {
}
