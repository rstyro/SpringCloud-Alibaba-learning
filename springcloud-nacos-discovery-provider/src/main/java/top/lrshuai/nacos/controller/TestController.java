package top.lrshuai.nacos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
