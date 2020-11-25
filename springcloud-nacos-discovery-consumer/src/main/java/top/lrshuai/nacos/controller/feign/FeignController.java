package top.lrshuai.nacos.controller.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.entity.User;

/**
 * feign调用
 */
@RequestMapping("/feign")
@RestController
public class FeignController {

    @Autowired
    NacosProviderFeignClient nacosProviderFeignClient;

    @GetMapping("/hello")
    public String hello(String name){
        String result = nacosProviderFeignClient.sayHi(name);
        return "feign访问provider返回 : " + result;
    }

    @GetMapping("/setUser")
    public String hello(@RequestBody User user){
        User result = nacosProviderFeignClient.setUser(user);
        return "feign访问provider返回 : " + result.toString();
    }
}
