package top.lrshuai.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.nacos.config.ExtensionConfig;
import top.lrshuai.nacos.config.ShareConfig;
import top.lrshuai.nacos.config.UserConfig;

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

    @Autowired
    private UserConfig userConfig;

    @Autowired
    private ShareConfig shareConfig;

    @Autowired
    private ExtensionConfig extensionConfig;


    @GetMapping("/getName")
    public String getName() {
        return this.name;
    }

    @GetMapping("/getUserConfig")
    public String getUserConfig() {
        return userConfig.toString();
    }

    @GetMapping("/getShareConfig")
    public String getShareConfig() {
        return shareConfig.toString();
    }

    @GetMapping("/getExtensionConfig")
    public String getExtensionConfig() {
        return extensionConfig.toString();
    }

}
