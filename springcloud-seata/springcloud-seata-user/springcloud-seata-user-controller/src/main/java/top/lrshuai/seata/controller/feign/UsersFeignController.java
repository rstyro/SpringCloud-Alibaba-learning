package top.lrshuai.seata.controller.feign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.service.user.service.IUsersService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/feign/user")
public class UsersFeignController {

    private IUsersService usersService;

    @Autowired
    public void setUsersService(IUsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/getUserInfo/{userId}")
    public Result getUserInfo(@PathVariable  Long userId){
        return Result.ok(usersService.getUserInfo(userId));
    }

}
