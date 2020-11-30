package top.lrshuai.nacos.controller.feign;

import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.entity.User;

@Component
public class MyNacosProviderFallback implements NacosProviderFeignClient{
    @Override
    public Result sayHi(String name) {
        return Result.error("sayHi 服务未找到，熔断");
    }

    @Override
    public Result setUser(User user) {
        System.out.println("服务未找到");
        return Result.error("服务未找到");
    }
}
