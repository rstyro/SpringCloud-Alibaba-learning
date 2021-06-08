package top.lrshuai.seata.service.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.seata.commons.user.dto.UpdateAccountDto;
import top.lrshuai.seata.commons.user.entity.UserAccount;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
public interface IUserAccountService extends IService<UserAccount> {
    /**
     * 获取用户账户信息
     */
    UserAccount getUserAccount(Long userId);

    /**
     * 更新账户
     */
    boolean operateAccount(UpdateAccountDto dto);
}
