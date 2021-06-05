package top.lrshuai.seata.controller.feign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.service.user.service.IUserAccountService;
import top.lrshuai.seata.user.commons.dto.UpdateAccountDto;

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
    public Result getUserInfo(@RequestBody UpdateAccountDto dto){
        return Result.ok(userAccountService.operateAccount(dto));
    }

    @GetMapping("/info/{userId}")
    public Result getUserInfo(@PathVariable Long userId){
        return Result.ok(userAccountService.getUserAccount(userId));
    }

}
