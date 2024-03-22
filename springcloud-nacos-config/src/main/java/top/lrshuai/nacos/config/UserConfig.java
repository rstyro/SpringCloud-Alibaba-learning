package top.lrshuai.nacos.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author rstyro
 */
@RefreshScope
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserConfig {

    private String name;

    private int age;

    @Override
    public String toString() {
        return "UserConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
