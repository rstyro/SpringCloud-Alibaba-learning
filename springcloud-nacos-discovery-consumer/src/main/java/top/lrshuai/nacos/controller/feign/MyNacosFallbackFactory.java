package top.lrshuai.nacos.controller.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.test.User;


@Component
public class MyNacosFallbackFactory implements FallbackFactory<NacosProviderFeignClient> {
    @Override
    public NacosProviderFeignClient create(Throwable cause) {
//        return new MyNacosProviderFallback();
        return new NacosProviderFeignClient() {
            @Override
            public R sayHi(String name) {
                return R.fail("sayHi 服务未找到，熔断");
            }

            @Override
            public R setUser(User user) {
                System.out.println("服务未找到");
                return R.fail("服务未找到");
            }
        };
    }
}
