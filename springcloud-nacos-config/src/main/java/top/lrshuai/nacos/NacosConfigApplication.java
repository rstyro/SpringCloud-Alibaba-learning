package top.lrshuai.nacos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author rstyro
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class NacosConfigApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NacosConfigApplication.class, args);

        while(true) {
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            Integer userAge = applicationContext.getEnvironment().getProperty("user.age",Integer.class);
            System.err.println("user name :"+userName+"; age: "+userAge);
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
