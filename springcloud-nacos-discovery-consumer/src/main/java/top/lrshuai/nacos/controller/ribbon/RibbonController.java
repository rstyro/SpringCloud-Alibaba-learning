package top.lrshuai.nacos.controller.ribbon;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.lrshuai.common.core.test.User;

import javax.annotation.Resource;

@RequestMapping("/ribbon")
@RestController
public class RibbonController {

    /**
     * 底层就是ribbon
     */
    @Resource
    LoadBalancerClient loadBalancerClient;


    @GetMapping("/hello")
    public String hello(String name){
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        String url = serviceInstance.getUri() + "/test/sayHi?name=" + name;
        String result = new RestTemplate().getForObject(url, String.class);
        return "访问 : " + url + ", 返回 : " + result;
    }

    @PostMapping("/setUser")
    public String setUser(@RequestBody User user){
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        String url = serviceInstance.getUri() + "/test/setUser" ;
        String result = new RestTemplate().postForObject(url, user, String.class);
        return "访问 : " + url + ", 返回 : " + result;
    }
}
