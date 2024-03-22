package top.lrshuai.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 自定义配置
 */
@RefreshScope
@Data
@Component
@ConfigurationProperties(prefix = "ext")
public class ExtensionConfig {
    private String name;

    private int age;

    @Override
    public String toString() {
        return "ExtensionConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
