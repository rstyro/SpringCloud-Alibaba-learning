package top.lrshuai.nacos.controller.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
}
