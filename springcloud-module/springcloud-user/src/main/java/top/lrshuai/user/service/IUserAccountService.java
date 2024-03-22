package top.lrshuai.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.user.api.dto.UpdateAccountDto;
import top.lrshuai.user.api.entity.UserAccount;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2024-03-15
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
