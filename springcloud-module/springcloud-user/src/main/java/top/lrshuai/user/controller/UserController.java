package top.lrshuai.user.controller;

import cn.hutool.core.net.NetUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.common.core.context.SecurityContextHolder;
import top.lrshuai.common.core.exception.ServiceException;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.web.BaseController;
import top.lrshuai.common.redis.config.util.RedisUtils;
import top.lrshuai.mall.api.entity.Commodity;
import top.lrshuai.mall.api.feign.ICommodityFeign;
import top.lrshuai.user.api.entity.User;
import top.lrshuai.user.service.IUserService;

import javax.annotation.Resource;

/**
 * 用户相关
 */
@Slf4j
@Tag(name = "user",description = "用户相关")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @Resource
    private ICommodityFeign commodityFeign;

    @Value("${server.port}")
    private int port;

    @Operation(summary = "测试")
    @GetMapping("/test")
    public R test(){
        if(SecurityContextHolder.getToken().equals("1")){
            throw new ServiceException().setMessage("测试报错了");
        }
        log.info("测试请求,{}:{}", NetUtil.getLocalhostStr(),port);
        return R.ok(SecurityContextHolder.getUserId(),SecurityContextHolder.getToken());
    }

    @Operation(summary = "测试feign")
    @GetMapping("/testCommodity")
    public R testCommodity(@RequestParam(required = false,defaultValue = "car") String code){
        log.info("测试请求,host={}:code={}", NetUtil.getLocalhostStr(),code);
        R<Commodity> infoByCode = commodityFeign.getInfoByCode(code);
        log.info("测试请求,result={}", JSON.toJSONString(infoByCode));
        return R.ok(infoByCode);
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
