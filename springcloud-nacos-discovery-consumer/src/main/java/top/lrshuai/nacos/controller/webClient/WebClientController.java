package top.lrshuai.nacos.controller.webClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * webClient 调用
 */
@RequestMapping("/webClient")
@RestController
public class WebClientController {

    @Autowired
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

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
