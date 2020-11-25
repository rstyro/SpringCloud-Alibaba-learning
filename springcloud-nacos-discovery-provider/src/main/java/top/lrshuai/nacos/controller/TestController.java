package top.lrshuai.nacos.controller;

import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.entity.User;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/sayHi")
    public String sayHi(String name){
        String result = "hello ";
        result = result.concat(name);
        System.out.println(result);
        return result;
    }

    @PostMapping("/setUser")
    public Object setUser(@RequestBody User user){
        System.out.println(user.toString());
        return user;
    }
}
