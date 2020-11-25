package top.lrshuai.nacos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author rstyro
 */
@RefreshScope
@Component
public class UserConfig {
    @Value("${user.name}")
    private String name;

    @Value("${user.age}")
    private int age;

    @Override
    public String toString() {
        return "UserConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
