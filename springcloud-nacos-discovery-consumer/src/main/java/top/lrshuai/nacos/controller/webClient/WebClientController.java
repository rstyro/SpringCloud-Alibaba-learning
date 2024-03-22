package top.lrshuai.nacos.controller.webClient;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import top.lrshuai.common.core.test.User;

import javax.annotation.Resource;

/**
 * webClient 调用
 */
@RequestMapping("/webClient")
@RestController
public class WebClientController {

    @Resource
    private WebClient.Builder webClientBuilder;


    @GetMapping("/hello")
    public Mono<String> hello(String name){
        Mono<String> result = webClientBuilder.build()
                .get()
                .uri("http://nacos-provider/test/sayHi?name="+name)
                .retrieve()
                .bodyToMono(String.class);
        return result;
    }

    @PostMapping("/setUser")
    public Mono<String> setUser(@RequestBody User user){
        Mono<String> result = webClientBuilder.build()
                .post()
                .uri("http://nacos-provider/test/setUser")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(String.class);
        return result;
    }

}
