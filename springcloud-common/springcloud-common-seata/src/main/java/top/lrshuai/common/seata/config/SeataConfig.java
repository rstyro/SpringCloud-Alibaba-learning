package top.lrshuai.common.seata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import top.lrshuai.common.core.factory.YmlPropertySourceFactory;

/**
 * seata的默认配置
 */
@Configuration
@PropertySource(value = "classpath:common-seata.yml", factory = YmlPropertySourceFactory.class)
public class SeataConfig {
}
