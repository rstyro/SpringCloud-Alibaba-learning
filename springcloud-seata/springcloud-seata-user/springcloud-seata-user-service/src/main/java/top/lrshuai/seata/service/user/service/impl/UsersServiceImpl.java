package top.lrshuai.seata.service.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lrshuai.seata.service.user.mapper.UsersMapper;
import top.lrshuai.seata.service.user.service.IUsersService;
import top.lrshuai.seata.user.commons.entity.Users;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {

    @Override
    public Users getUserInfo(Long userId) {
        return this.getById(userId).setPassword("***");
    }
}
