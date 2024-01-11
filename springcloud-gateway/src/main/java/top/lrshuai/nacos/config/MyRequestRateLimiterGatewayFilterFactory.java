package top.lrshuai.nacos.config;

import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import top.lrshuai.nacos.commons.Result;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 仿造 {@link RequestRateLimiterGatewayFilterFactory }
 * 通过redis 令牌桶限流{@link RedisRateLimiter }
 * 自定义限流返回结果
 */
@Component
public class MyRequestRateLimiterGatewayFilterFactory extends AbstractGatewayFilterFactory<MyRequestRateLimiterGatewayFilterFactory.Config>{
    public static final String KEY_RESOLVER_KEY = "keyResolver";
    private final RateLimiter defaultRateLimiter;
    private final KeyResolver defaultKeyResolver;

    /**
     * 默认 ip KeyResolver
     * @param defaultRateLimiter
     * @param defaultKeyResolver
     */
    public MyRequestRateLimiterGatewayFilterFactory(RateLimiter defaultRateLimiter, @Qualifier("ipKeyResolver") KeyResolver defaultKeyResolver) {
        super(Config.class);
        this.defaultRateLimiter = defaultRateLimiter;
        this.defaultKeyResolver = defaultKeyResolver;
    }

    public KeyResolver getDefaultKeyResolver() {
        return defaultKeyResolver;
    }

    public RateLimiter getDefaultRateLimiter() {
        return defaultRateLimiter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public GatewayFilter apply(Config config) {
        KeyResolver resolver = (config.keyResolver == null) ? defaultKeyResolver : config.keyResolver;
        RateLimiter<Object> limiter = (config.rateLimiter == null) ? defaultRateLimiter : config.rateLimiter;
        return (exchange, chain) -> {
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            return resolver.resolve(exchange).flatMap(key ->
                    limiter.isAllowed(route.getId(), key).flatMap(response -> {
                        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                            exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
                        }
                        if (response.isAllowed()) {
                            return chain.filter(exchange);
                        }
                        ServerHttpResponse httpResponse = exchange.getResponse();
                        httpResponse.setStatusCode(config.getStatusCode());
                        httpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                        Result error = Result.error("访问已限流，请稍候再请求");
                        DataBuffer buffer = httpResponse.bufferFactory().wrap(JSON.toJSONString(error).getBytes(StandardCharsets.UTF_8));
                        return httpResponse.writeWith(Mono.just(buffer));
                    }));
        };
    }

    public static class Config {
        private KeyResolver keyResolver;
        private RateLimiter rateLimiter;
        private HttpStatus statusCode = HttpStatus.TOO_MANY_REQUESTS;

        public KeyResolver getKeyResolver() {
            return keyResolver;
        }

        public Config setKeyResolver(KeyResolver keyResolver) {
            this.keyResolver = keyResolver;
            return this;
        }
        public RateLimiter getRateLimiter() {
            return rateLimiter;
        }

        public Config setRateLimiter(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
            return this;
        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }

        public Config setStatusCode(HttpStatus statusCode) {
            this.statusCode = statusCode;
            return this;
        }
    }
}
