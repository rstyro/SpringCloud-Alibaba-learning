package top.lrshuai.nacos.controller.feign;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.Result;
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
    @RequiresPermissions("user:list")
    public Result hello(String name){
        Result result = nacosProviderFeignClient.sayHi(name);
        System.out.println("feign访问provider返回 : "+result);
        return  result;
    }

    @GetMapping("/setUser")
    @RequiresPermissions("user:add")
    public Result hello(@RequestBody User user){
        Result result = nacosProviderFeignClient.setUser(user);
        System.out.println("feign访问provider返回 : "+result);
        return result;
    }
}
