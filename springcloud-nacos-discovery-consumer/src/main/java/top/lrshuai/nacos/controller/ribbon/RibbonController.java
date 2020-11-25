package top.lrshuai.nacos.controller.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.lrshuai.nacos.commons.entity.User;

@RequestMapping("/ribbon")
@RestController
public class RibbonController {

    /**
     * 底层就是ribbon
     */
    @Autowired
    LoadBalancerClient loadBalancerClient;


    @GetMapping("/hello")
    public String hello(String name){
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        String url = serviceInstance.getUri() + "/test/sayHi?name=" + name;
        String result = new RestTemplate().getForObject(url, String.class);
        return "访问 : " + url + ", 返回 : " + result;
    }

    @PostMapping("/setUser")
    public String setUser(@RequestBody  User user){
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        String url = serviceInstance.getUri() + "/test/setUser" ;
        String result = new RestTemplate().postForObject(url, user, String.class);
        return "访问 : " + url + ", 返回 : " + result;
    }
}
