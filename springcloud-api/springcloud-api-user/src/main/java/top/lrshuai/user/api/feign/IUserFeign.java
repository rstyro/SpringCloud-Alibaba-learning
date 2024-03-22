package top.lrshuai.user.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.lrshuai.common.core.constant.ServiceName;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.user.api.entity.User;
import top.lrshuai.user.api.feign.fallback.UserFallbackFactory;


@FeignClient(name = ServiceName.USER,fallbackFactory = UserFallbackFactory.class)
@Component
public interface IUserFeign {

    @GetMapping("/feign/user/getUserInfo/{userId}")
    R<User> getUserInfo(@PathVariable("userId") Long userId);


}
