package top.lrshuai.common.core.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.lang.annotation.*;

/**
 * 自定义扫描 各个模块的 包
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ComponentScans(value = {
        @ComponentScan(value = "top.lrshuai.common.**"),
        @ComponentScan(value = "top.lrshuai.*.api.**")
})
public @interface CloudComponentScan {
}
