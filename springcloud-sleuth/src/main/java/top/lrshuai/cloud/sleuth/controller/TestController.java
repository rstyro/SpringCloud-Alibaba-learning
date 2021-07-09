package top.lrshuai.cloud.sleuth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.entity.User;

@RestController
@RequestMapping("/test")
public class TestController {

    RestTemplate loadRestTemplate;
    @Autowired
    public void setLoadRestTemplate(RestTemplate loadRestTemplate) {
        this.loadRestTemplate = loadRestTemplate;
    }

    @GetMapping("/get")
    public Result get(){
        return Result.ok();
    }

    @GetMapping("/hello")
    public String hello(String name){
        Result result = loadRestTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, Result.class);
        return "访问provider 返回 : " + result;
    }

    @GetMapping("/hello2")
    public String hello2(String name){
        Result result1 = loadRestTemplate.getForObject("http://nacos-consumer/feign/hello?name="+name, Result.class);
        Result result2 = loadRestTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, Result.class);
        return "访问provider 返回 : " + result2;
    }

    @PostMapping("/setUser")
    public String setUser(@RequestBody User user){
        Result result = loadRestTemplate.postForObject("http://nacos-provider/test/setUser", user, Result.class);
        return "访问provider 返回 : " + result;
    }
}
