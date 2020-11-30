package top.lrshuai.nacos.controller;

import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.entity.User;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/sayHi")
    public Result sayHi(String name){
        String result = "hello ";
        result = result.concat(name);
        System.out.println(result);
        return Result.ok(result);
    }

    @PostMapping("/setUser")
    public Result setUser(@RequestBody User user){
        System.out.println(user.toString());
        return Result.ok(user);
    }
}
