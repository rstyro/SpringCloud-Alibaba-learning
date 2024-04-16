package top.lrshuai.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import top.lrshuai.common.core.constant.SecurityConst;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DeveloperRoutingFilter implements GlobalFilter, Ordered {
    // 注入DiscoveryClient以便获取服务实例信息
    @Resource
    private DiscoveryClient discoveryClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取请求中携带的开发者标识
        String targetDeveloper = request.getHeaders().getFirst(SecurityConst.DEVELOPER);
        if(StringUtils.hasLength(targetDeveloper)){
            log.info("目标developer={}", targetDeveloper);
            // 获取当前路由
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            String serviceId = route.getId();
            // 获取所有可用的服务实例
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            Optional<ServiceInstance> targetInstance = instances.stream()
                    .filter(instance -> instance.getMetadata().containsKey(SecurityConst.DEVELOPER) &&
                            instance.getMetadata().get(SecurityConst.DEVELOPER).equals(targetDeveloper))
                    .findFirst();
            if (targetInstance.isPresent()) {
                // 更新请求的目标URI
                URI uri = UriComponentsBuilder.fromUri(request.getURI())
                        .host(targetInstance.get().getHost())
                        .port(targetInstance.get().getPort()).build().toUri();
                if (log.isDebugEnabled()) {
                    log.info("url chosen={}", uri);
                }
                // 覆盖 ReactiveLoadBalancerClientFilter的uri
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, uri);
            }
        }
        // 继续执行下一个过滤器
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 在ReactiveLoadBalancerClientFilter之后执行
        return ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER + 1;
    }
}
