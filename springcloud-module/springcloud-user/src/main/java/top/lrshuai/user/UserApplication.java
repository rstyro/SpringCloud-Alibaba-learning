package top.lrshuai.user;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import top.lrshuai.common.core.annotation.CloudComponentScan;
import top.lrshuai.common.security.annotation.EnableFeignClientsPlus;
import top.lrshuai.common.swagger.annotation.EnableCustomSwagger3;


/**
 * 用户模块
 */
@Slf4j
@CloudComponentScan
@EnableCustomSwagger3
@EnableDiscoveryClient
@EnableFeignClientsPlus
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(UserApplication.class, args);
        printfUrl(application);
    }

    /**
     * 打印地址
     * @param application
     */
    public static void printfUrl(ConfigurableApplicationContext application){
        Environment env = application.getEnvironment();
        String ip = NetUtil.getLocalhostStr();
        String port = env.getProperty("server.port");
        String property = env.getProperty("server.servlet.context-path");
        String path = property == null ? "" :  property;
        log.info("用户模块启动成功,{}:{}{}", ip,port,path);
    }
}
