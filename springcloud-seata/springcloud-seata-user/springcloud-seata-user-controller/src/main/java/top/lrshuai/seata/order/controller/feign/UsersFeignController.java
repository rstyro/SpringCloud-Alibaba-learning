package top.lrshuai.seata.order.controller.feign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.seata.service.user.service.IUsersService;
import top.lrshuai.seata.commons.user.entity.Users;

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
    public RemoteResult<Users> getUserInfo(@PathVariable  Long userId){
        return RemoteResult.data(usersService.getUserInfo(userId));
    }

}
