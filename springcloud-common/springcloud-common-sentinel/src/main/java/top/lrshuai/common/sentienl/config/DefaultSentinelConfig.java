package top.lrshuai.common.sentienl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import top.lrshuai.common.core.factory.YmlPropertySourceFactory;

@Configuration
@PropertySource(value = "classpath:common-sentinel.yml", factory = YmlPropertySourceFactory.class)
public class DefaultSentinelConfig {
}
