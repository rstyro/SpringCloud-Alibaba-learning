package top.lrshuai.nacos.controller.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
