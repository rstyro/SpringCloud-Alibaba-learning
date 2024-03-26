package top.lrshuai.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.gateway.utils.MonoUtils;

/**
 * 自定义限流异常处理
 */
@Slf4j
public class SentinelFallbackHandler implements WebExceptionHandler {
    private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange,Throwable e) {
        // 默认流控
        ApiResultEnum apiResultEnum=ApiResultEnum.BLOCKED_FLOW;
        if (e instanceof FlowException) {
            log.error("触发流控，url={}",exchange.getRequest().getPath());
            apiResultEnum=ApiResultEnum.BLOCKED_FLOW;
        } else if(e instanceof DegradeException) {
            log.error("触发熔断降级，url={}",exchange.getRequest().getPath());
            apiResultEnum=ApiResultEnum.BLOCKED_DEGRADE;
        } else if (e instanceof AuthorityException) {
            log.error("请求未授权，url={}",exchange.getRequest().getPath());
            apiResultEnum=ApiResultEnum.BLOCKED_AUTHORITY;
        }
        return MonoUtils.webFluxResponseWriter(exchange.getResponse(), apiResultEnum);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange,ex));
    }

    private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
        return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
    }
}
