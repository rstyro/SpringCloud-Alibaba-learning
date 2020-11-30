package top.lrshuai.nacos.controller.restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.entity.User;

@RequestMapping("/rest")
@RestController
public class RestTemplateController {

    @Autowired
    RestTemplate loadRestTemplate;

    @GetMapping("/hello")
    public String hello(String name){
        Result result = loadRestTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, Result.class);
        return "访问provider 返回 : " + result;
    }

    @PostMapping("/setUser")
    public String setUser(@RequestBody User user){
        Result result = loadRestTemplate.postForObject("http://nacos-provider/test/setUser", user, Result.class);
        return "访问provider 返回 : " + result;
    }

    @Bean
    @LoadBalanced
    public RestTemplate loadRestTemplate(){
        return  new RestTemplate();
    }
}
