package top.lrshuai.nacos.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nacos-provider")
public interface NacosProviderFeignClient {

    @GetMapping("/test/sayHi")
    // @RequestParam 用于参数映射不然会报: [405] 异常
    String sayHi(@RequestParam("name") String name);
}
