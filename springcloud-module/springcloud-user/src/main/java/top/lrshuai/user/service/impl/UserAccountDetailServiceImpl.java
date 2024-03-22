package top.lrshuai.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lrshuai.user.api.entity.UserAccountDetail;
import top.lrshuai.user.mapper.UserAccountDetailMapper;
import top.lrshuai.user.service.IUserAccountDetailService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2024-03-15
 */
@Service
public class UserAccountDetailServiceImpl extends ServiceImpl<UserAccountDetailMapper, UserAccountDetail> implements IUserAccountDetailService {

}
