package top.lrshuai.seata.feign.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.nacos.commons.consts.ServiceName;
import top.lrshuai.seata.feign.user.fallback.UserFallbackFactory;
import top.lrshuai.seata.commons.user.entity.Users;

@FeignClient(name = ServiceName.USER,fallbackFactory = UserFallbackFactory.class)
@Component
public interface IUserFeign {

    @GetMapping("/feign/user/getUserInfo/{userId}")
    RemoteResult<Users> getUserInfo(@PathVariable("userId") Long userId);


}
