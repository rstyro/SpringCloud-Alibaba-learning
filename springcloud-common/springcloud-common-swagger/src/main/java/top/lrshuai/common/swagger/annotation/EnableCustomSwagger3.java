package top.lrshuai.common.swagger.annotation;

import org.springframework.context.annotation.Import;
import top.lrshuai.common.swagger.config.SpringdocAutoConfiguration;

import java.lang.annotation.*;

/**
 * 使用这个注解即可集成swagger
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SpringdocAutoConfiguration.class})
public @interface EnableCustomSwagger3 {

}
