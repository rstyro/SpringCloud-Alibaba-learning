package top.lrshuai.nacos.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.lrshuai.nacos.commons.entity.User;

@FeignClient(name = "nacos-provider",fallbackFactory = MyNacosFallbackFactory.class)
@Component
public interface NacosProviderFeignClient {

    @GetMapping("/test/sayHi")
    // @RequestParam 用于参数映射不然会报: [405] 异常
    String sayHi(@RequestParam("name") String name);

    @PostMapping("/test/setUser")
    User setUser(User user);
}
