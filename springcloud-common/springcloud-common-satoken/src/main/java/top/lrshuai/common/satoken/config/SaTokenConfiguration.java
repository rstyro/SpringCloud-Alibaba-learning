package top.lrshuai.common.satoken.config;

import cn.dev33.satoken.dao.SaTokenDao;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import top.lrshuai.common.core.factory.YmlPropertySourceFactory;
import top.lrshuai.common.satoken.core.RedisSaTokenDao;

/**
 * Sa-Token 配置
 */
@AutoConfiguration
@PropertySource(value = "classpath:common-satoken.yml", factory = YmlPropertySourceFactory.class)
public class SaTokenConfiguration {

    /**
     * 自定义dao层存储
     */
    @Bean
    public SaTokenDao saTokenDao() {
        return new RedisSaTokenDao();
    }

}
