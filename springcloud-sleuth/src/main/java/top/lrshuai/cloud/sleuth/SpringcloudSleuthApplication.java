package top.lrshuai.cloud.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudSleuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudSleuthApplication.class, args);
	}

}
