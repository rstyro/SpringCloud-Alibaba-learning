package top.lrshuai.common.sleuth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import top.lrshuai.common.core.factory.YmlPropertySourceFactory;

@Configuration
@PropertySource(value = "classpath:common-sleuth-zipkin.yml", factory = YmlPropertySourceFactory.class)
public class DefaultSleuthZipkinConfig {
}
