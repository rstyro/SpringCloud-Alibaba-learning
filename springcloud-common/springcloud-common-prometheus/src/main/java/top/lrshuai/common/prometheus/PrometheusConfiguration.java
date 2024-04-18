package top.lrshuai.common.prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * prometheus 配置
 */
@AutoConfiguration
public class PrometheusConfiguration {
    /**
     * 添加全局标签，便于在监控系统中进行分组和筛选。
     * @param applicationName appName
     */
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
}
