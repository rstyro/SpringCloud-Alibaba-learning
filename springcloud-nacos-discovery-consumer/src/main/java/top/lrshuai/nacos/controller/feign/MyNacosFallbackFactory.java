package top.lrshuai.nacos.controller.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;


@Component
public class MyNacosFallbackFactory implements FallbackFactory<NacosProviderFeignClient> {
    @Override
    public NacosProviderFeignClient create(Throwable cause) {
        return new MyNacosProviderFallback();
    }
}
