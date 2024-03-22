package top.lrshuai.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 共享配置
 */
@RefreshScope
@Data
@Component
@ConfigurationProperties(prefix = "myshare")
public class ShareConfig {

    private String name;

    private int age;

    @Override
    public String toString() {
        return "ShareConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
