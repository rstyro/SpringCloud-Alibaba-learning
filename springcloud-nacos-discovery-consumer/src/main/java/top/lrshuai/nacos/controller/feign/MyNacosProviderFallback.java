package top.lrshuai.nacos.controller.feign;

import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.entity.User;

@Component
public class MyNacosProviderFallback implements NacosProviderFeignClient{
    @Override
    public String sayHi(String name) {
        return "sayHi 服务未找到，熔断";
    }

    @Override
    public User setUser(User user) {
        System.out.println("服务未找到");
        return new User();
    }
}
