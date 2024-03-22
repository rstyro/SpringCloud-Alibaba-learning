package top.lrshuai.nacos.controller.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.test.User;

import javax.annotation.Resource;

/**
 * feign调用
 */
@RequestMapping("/feign")
@RestController
public class FeignController {

    @Resource
    NacosProviderFeignClient nacosProviderFeignClient;

    @GetMapping("/hello")
    public R hello(String name){
        R result = nacosProviderFeignClient.sayHi(name);
        System.out.println("feign访问provider返回 : "+result);
        return  result;
    }

    @GetMapping("/setUser")
    public R hello(@RequestBody User user){
        R result = nacosProviderFeignClient.setUser(user);
        System.out.println("feign访问provider返回 : "+result);
        return result;
    }
}
