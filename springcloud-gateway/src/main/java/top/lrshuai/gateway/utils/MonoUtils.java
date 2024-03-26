package top.lrshuai.gateway.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.resp.R;

public class MonoUtils {

    /**
     * 设置webflux模型响应
     *
     * @param response ServerHttpResponse
     * @param status   http状态码
     * @param code     响应状态码
     * @param message    响应内容
     * @return Mono<Void>
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, HttpStatus status, String message, int code) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        R result = R.fail(code, message);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSONObject.toJSONString(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String message, int code) {
        return webFluxResponseWriter(response,HttpStatus.OK,message,code);
    }

    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, ApiResultEnum apiResultEnum) {
        return webFluxResponseWriter(response,HttpStatus.OK,apiResultEnum.getMessage(),apiResultEnum.getCode());
    }
}
