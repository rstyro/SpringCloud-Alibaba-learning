package top.lrshuai.seata.service.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.seata.commons.user.entity.Users;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
public interface IUsersService extends IService<Users> {
    Users getUserInfo(Long userId);
}
