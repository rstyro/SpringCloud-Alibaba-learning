package top.lrshuai.nacos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 自定义配置
 */
@RefreshScope
@Component
public class ExtensionConfig {
    @Value("${ext.name}")
    private String name;

    @Value("${ext.age}")
    private int age;

    @Override
    public String toString() {
        return "UserConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
