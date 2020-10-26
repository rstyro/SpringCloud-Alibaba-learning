package top.lrshuai.nacos.controller.restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/rest")
@RestController
public class RestTemplateController {

    @Autowired
    RestTemplate loadRestTemplate;

    @GetMapping("/hello")
    public String hello(String name){
        String result = loadRestTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, String.class);
        return "访问provider 返回 : " + result;
    }

    @Bean
    @LoadBalanced
    public RestTemplate loadRestTemplate(){
        return  new RestTemplate();
    }
}
