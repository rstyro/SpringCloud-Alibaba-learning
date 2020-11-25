package top.lrshuai.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.nacos.config.UserConfig;

/**
 * 测试
 * @author rstyro
 */
@RequestMapping("/config")
@RestController
@RefreshScope
public class TestController {

//    @Value("${user.name}")
//    private String name;


    @Autowired
    private UserConfig userConfig;


//    @GetMapping("/getUser")
//    public String getUser() {
//        return this.name;
//    }

    @GetMapping("/getUserConfig")
    public String getUserConfig() {
        return userConfig.toString();
    }

}
