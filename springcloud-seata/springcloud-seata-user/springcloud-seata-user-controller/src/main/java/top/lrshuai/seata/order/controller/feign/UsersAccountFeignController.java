package top.lrshuai.seata.order.controller.feign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.seata.service.user.service.IUserAccountService;
import top.lrshuai.seata.commons.user.dto.UpdateAccountDto;
import top.lrshuai.seata.commons.user.entity.UserAccount;

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
public class UsersAccountFeignController {

    private IUserAccountService userAccountService;

    @Autowired
    public void setUserAccountService(IUserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/operateAccount")
    public RemoteResult<Boolean> operateAccount(@RequestBody UpdateAccountDto dto){
        return RemoteResult.data(userAccountService.operateAccount(dto));
    }

    @GetMapping("/info/{userId}")
    public RemoteResult<UserAccount> getUserAccount(@PathVariable Long userId){
        return RemoteResult.data(userAccountService.getUserAccount(userId));
    }

}
