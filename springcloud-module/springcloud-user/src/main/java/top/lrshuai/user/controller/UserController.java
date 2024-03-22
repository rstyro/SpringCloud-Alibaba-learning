package top.lrshuai.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.common.core.context.SecurityContextHolder;
import top.lrshuai.common.core.exception.ServiceException;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.web.BaseController;
import top.lrshuai.common.redis.config.util.RedisUtils;
import top.lrshuai.user.api.entity.User;
import top.lrshuai.user.service.IUserService;

import javax.annotation.Resource;

/**
 * 用户相关
 */
@Tag(name = "user",description = "用户相关")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @Operation(summary = "测试")
    @GetMapping("/test")
    public R test(){
        if(SecurityContextHolder.getToken().equals("1")){
            throw new ServiceException().setMessage("测试报错了");
        }
        return R.ok(SecurityContextHolder.getUserId(),SecurityContextHolder.getToken());
    }

    @Operation(summary = "通过ID获取用户信息")
    @GetMapping("/getUserById")
    public R getUserById(Long id){
        RedisUtils.setCacheObject("test",id);
        return R.ok(userService.getById(id));
    }

    @Operation(summary = "分页获取用户信息")
    @GetMapping("/getUserByPage")
    public R getUserByPage(){
        return R.ok(userService.page(new Page<>(SecurityContextHolder.getPageNo(),SecurityContextHolder.getPageSize()),new LambdaQueryWrapper<User>().gt(User::getId,1)));
    }

    @Operation(summary = "通过ID获取用户信息")
    @GetMapping("/info/{userId}")
    public R getUserInfo(@PathVariable Long userId){
        return R.ok(userService.getById(userId));
    }

    @GetMapping("/update/{userId}")
    public R updateUser(@PathVariable Long userId, User user){
        return R.ok(userService.updateById(user.setId(userId)));
    }

}
