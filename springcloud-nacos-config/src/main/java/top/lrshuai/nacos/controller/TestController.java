package top.lrshuai.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.nacos.config.ExtensionConfig;
import top.lrshuai.nacos.config.ShareConfig;
import top.lrshuai.nacos.config.UserConfig;

import javax.annotation.Resource;

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

    @Resource
    private UserConfig userConfig;

    @Resource
    private ShareConfig shareConfig;

    @Resource
    private ExtensionConfig extensionConfig;

    @Resource
    private ConfigurableApplicationContext applicationContext;


    @GetMapping("/getName")
    public String getName() {
        return this.name;
    }

    @GetMapping("/getConfig")
    public String getConfig() {
        return applicationContext.getEnvironment().getProperty("user.name")+applicationContext.getEnvironment().getProperty("user.age",Integer.class);
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
