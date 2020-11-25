package top.lrshuai.nacos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 共享配置
 */
@RefreshScope
@Component
public class ShareConfig {
    @Value("${myshare.name}")
    private String name;

    @Value("${myshare.age}")
    private int age;

    @Override
    public String toString() {
        return "UserConfig{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
