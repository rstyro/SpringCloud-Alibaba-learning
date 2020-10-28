package top.lrshuai.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * @author rstyro
 */
@RequestMapping("/config")
@RestController
@RefreshScope
public class TestController {

    @Value("${user.name}")
    private String name;

    @GetMapping("/getUser")
    public String getUser() {
        return this.name;
    }
}
