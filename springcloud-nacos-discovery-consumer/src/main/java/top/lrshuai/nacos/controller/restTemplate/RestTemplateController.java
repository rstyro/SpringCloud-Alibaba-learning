package top.lrshuai.nacos.controller.restTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.lrshuai.nacos.commons.entity.User;

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

    @GetMapping("/config")
    public String config(){
        String result = loadRestTemplate.getForObject("http://nacos-config/config/getUserConfig", String.class);
        return "访问provider 返回 : " + result;
    }

    @PostMapping("/setUser")
    public String setUser(@RequestBody User user){
        String result = loadRestTemplate.postForObject("http://nacos-provider/test/setUser", user, String.class);
        return "访问provider 返回 : " + result;
    }

    @Bean
    @LoadBalanced
    public RestTemplate loadRestTemplate(){
        return  new RestTemplate();
    }
}
