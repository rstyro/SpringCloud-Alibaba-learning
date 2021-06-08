package top.lrshuai.seata.service.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.lrshuai.seata.service.user.mapper.UserAccountDetailMapper;
import top.lrshuai.seata.service.user.service.IUserAccountDetailService;
import top.lrshuai.seata.commons.user.entity.UserAccountDetail;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Service
public class UserAccountDetailServiceImpl extends ServiceImpl<UserAccountDetailMapper, UserAccountDetail> implements IUserAccountDetailService {

}
