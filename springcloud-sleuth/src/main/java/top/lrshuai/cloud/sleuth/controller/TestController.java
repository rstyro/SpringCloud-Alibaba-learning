package top.lrshuai.cloud.sleuth.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.test.User;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    RestTemplate loadRestTemplate;

    @GetMapping("/get")
    public R get(){
        return R.ok();
    }

    @GetMapping("/hello")
    public String hello(String name){
        R result = loadRestTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, R.class);
        return "访问provider 返回 : " + result;
    }

    @GetMapping("/hello2")
    public String hello2(String name){
        R result1 = loadRestTemplate.getForObject("http://nacos-consumer/feign/hello?name="+name, R.class);
        R result2 = loadRestTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, R.class);
        return "访问provider 返回 : " + result2;
    }

    @PostMapping("/setUser")
    public String setUser(@RequestBody User user){
        R result = loadRestTemplate.postForObject("http://nacos-provider/test/setUser", user, R.class);
        return "访问provider 返回 : " + result;
    }
}
