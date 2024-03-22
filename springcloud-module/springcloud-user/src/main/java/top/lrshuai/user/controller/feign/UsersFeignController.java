package top.lrshuai.user.controller.feign;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.web.BaseController;
import top.lrshuai.user.api.entity.User;
import top.lrshuai.user.api.feign.IUserFeign;
import top.lrshuai.user.service.IUserService;

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
@RequestMapping("/feign/user")
public class UsersFeignController extends BaseController implements IUserFeign {

    @Resource
    private IUserService userService;


    @GetMapping("/getUserInfo/{userId}")
    public R<User> getUserInfo(@PathVariable  Long userId){
        return R.ok(userService.getById(userId));
    }

}
