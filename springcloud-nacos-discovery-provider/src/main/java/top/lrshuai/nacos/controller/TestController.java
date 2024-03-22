package top.lrshuai.nacos.controller;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.common.core.resp.R;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/sayHi")
    public R sayHi(String name) throws InterruptedException {
        String result = "hello ";
//        Thread.sleep(5000l);
        result = result.concat(name);
        System.out.println(result);
        return R.ok(result);
    }

    @PostMapping("/setUser")
    public R setUser(@RequestBody User user){
        System.out.println(user.toString());
        return R.ok(user);
    }
}
