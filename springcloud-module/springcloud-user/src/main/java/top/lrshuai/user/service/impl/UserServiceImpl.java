package top.lrshuai.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lrshuai.user.api.entity.User;
import top.lrshuai.user.mapper.UserMapper;
import top.lrshuai.user.service.IUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lrs
 * @since 2024-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
