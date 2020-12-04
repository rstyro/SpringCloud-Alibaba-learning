package top.lrshuai.nacos.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 定义限流的 keyResolver
 */
@Configuration
public class RateKeyResolverConfig {
    /**
     * 按照Path限流
     * @return key
     */
    @Bean(name="pathKeyResolver")
    @Primary
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

    /**
     * 按照请求参数限流
     * @return
     */
    @Bean(name="userTokenKeyResolver")
    KeyResolver userTokenKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userToken"));
    }


    /**
     * 按照ip限流
     * @return
     */
    @Bean(name="ipKeyResolver")
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

}
