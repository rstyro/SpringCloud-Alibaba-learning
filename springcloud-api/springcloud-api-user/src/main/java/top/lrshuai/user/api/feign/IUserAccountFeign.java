package top.lrshuai.user.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.lrshuai.common.core.constant.ServiceName;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.user.api.dto.UpdateAccountDto;
import top.lrshuai.user.api.entity.UserAccount;
import top.lrshuai.user.api.feign.fallback.UserAccountFallbackFactory;


@FeignClient(name = ServiceName.USER,fallbackFactory = UserAccountFallbackFactory.class)
@Component
public interface IUserAccountFeign {

    @GetMapping("/feign/userAccount/info/{userId}")
    R<UserAccount> getAccountInfo(@PathVariable("userId") Long userId);

    @PostMapping("/feign/userAccount/operateAccount")
    R<Boolean> operateAccount(@RequestBody UpdateAccountDto dto);

}
