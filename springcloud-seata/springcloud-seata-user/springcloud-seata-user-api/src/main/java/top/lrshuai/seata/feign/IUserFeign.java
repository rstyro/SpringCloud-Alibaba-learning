package top.lrshuai.seata.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.consts.ServiceName;
import top.lrshuai.seata.feign.fallback.UserFallbackFactory;

@FeignClient(name = ServiceName.USER,fallbackFactory = UserFallbackFactory.class)
@Component
public interface IUserFeign {

    @GetMapping("/feign/user/getUserInfo/{userId}")
    RemoteResult getUserInfo(@PathVariable("userId") Long userId);


}
