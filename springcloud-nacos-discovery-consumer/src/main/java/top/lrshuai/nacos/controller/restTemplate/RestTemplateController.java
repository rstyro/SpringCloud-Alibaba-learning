package top.lrshuai.nacos.controller.restTemplate;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.test.User;

@RequestMapping("/rest")
@RestController
public class RestTemplateController {

    @Autowired
    RestTemplate loadRestTemplate;

    @GetMapping("/hello")
    public String hello(String name){
        R result = loadRestTemplate.getForObject("http://nacos-provider/test/sayHi?name="+name, R.class);
        return "访问provider 返回 : " + JSON.toJSONString(result);
    }

    @PostMapping("/setUser")
    public String setUser(@RequestBody User user){
        R result = loadRestTemplate.postForObject("http://nacos-provider/test/setUser", user, R.class);
        return "访问provider 返回 : " + JSON.toJSONString(result);
    }


}
