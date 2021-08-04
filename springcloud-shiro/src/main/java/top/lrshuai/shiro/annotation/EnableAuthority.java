package top.lrshuai.shiro.annotation;

import org.springframework.context.annotation.Import;
import top.lrshuai.shiro.config.ShiroConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在启动类上添加该注解来----开启-是否开启权限校验
 * @author rstyro
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ShiroConfig.class)
public @interface EnableAuthority {

}
