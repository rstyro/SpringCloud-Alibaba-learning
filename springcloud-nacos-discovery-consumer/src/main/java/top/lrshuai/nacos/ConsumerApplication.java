package top.lrshuai.nacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import top.lrshuai.cloud.commons.annotation.EnableExceptionHandler;

@ComponentScans(value = {
        @ComponentScan(value="top.lrshuai.shiro.**"),
        @ComponentScan(value = "top.lrshuai.cloud.commons.**")
})
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableExceptionHandler
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
