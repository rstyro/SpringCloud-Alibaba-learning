package top.lrshuai.user.controller.feign;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.web.BaseController;
import top.lrshuai.user.api.dto.UpdateAccountDto;
import top.lrshuai.user.api.entity.UserAccount;
import top.lrshuai.user.api.feign.IUserAccountFeign;
import top.lrshuai.user.service.IUserAccountService;

import javax.annotation.Resource;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/feign/userAccount")
public class UsersAccountFeignController extends BaseController implements IUserAccountFeign {

    @Resource
    private IUserAccountService userAccountService;

    @SentinelResource(value = "operateAccount")
    @PostMapping("/operateAccount")
    @Override
    public R<Boolean> operateAccount(@RequestBody UpdateAccountDto dto){
        return R.ok(userAccountService.operateAccount(dto));
    }

    @GetMapping("/info/{userId}")
    @Override
    public R<UserAccount> getAccountInfo(@PathVariable("userId") Long userId){
        return R.ok(userAccountService.getUserAccount(userId));
    }

}
