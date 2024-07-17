package top.lrshuai.common.encrypt.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import top.lrshuai.common.core.factory.YmlPropertySourceFactory;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "api.encrypt.enable", havingValue = "true",matchIfMissing = true)
@Import({EncryptProperties.class})
@PropertySource(value = "classpath:common-encrypt.yml", factory = YmlPropertySourceFactory.class)
public class ApiEncryptAutoConfiguration {
    public ApiEncryptAutoConfiguration() {
        log.info("api接口加密生效");
    }
}
