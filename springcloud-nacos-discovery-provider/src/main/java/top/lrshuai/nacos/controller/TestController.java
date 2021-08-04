package top.lrshuai.nacos.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.entity.User;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/sayHi")
    @RequiresPermissions("user:list")
    public Result sayHi(String name) throws InterruptedException {
        String result = "hello ";
//        Thread.sleep(5000l);
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
