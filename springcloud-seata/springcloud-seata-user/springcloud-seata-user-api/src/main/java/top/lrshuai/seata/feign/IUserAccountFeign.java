package top.lrshuai.seata.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.nacos.commons.consts.ServiceName;
import top.lrshuai.seata.feign.fallback.UserAccountFallbackFactory;
import top.lrshuai.seata.user.commons.dto.UpdateAccountDto;

@FeignClient(name = ServiceName.USER,fallbackFactory = UserAccountFallbackFactory.class)
@Component
public interface IUserAccountFeign {

    @GetMapping("/feign/userAccount/info/{userId}")
    RemoteResult getAccountInfo(@PathVariable("userId") Long userId);

    @PostMapping("/feign/userAccount/operateAccount")
    RemoteResult operateAccount(UpdateAccountDto dto);

}
