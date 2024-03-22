package top.lrshuai.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.utils.ErrorUtils;
import top.lrshuai.user.api.dto.UpdateAccountDto;
import top.lrshuai.user.api.entity.UserAccount;
import top.lrshuai.user.api.entity.UserAccountDetail;
import top.lrshuai.user.mapper.UserAccountMapper;
import top.lrshuai.user.service.IUserAccountDetailService;
import top.lrshuai.user.service.IUserAccountService;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2024-03-15
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

    @Resource
    private IUserAccountDetailService userAccountDetailService;

    @Override
    public UserAccount getUserAccount(Long userId) {
        return this.getOne(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getUserId,userId));
    }

    /**
     * 更新账户
     */
    @Override
    @Transactional
    public boolean operateAccount(UpdateAccountDto dto) {
        UserAccount userAccount = getUserAccount(dto.getUserId());
        UserAccountDetail userAccountDetail = new UserAccountDetail();
        userAccountDetail.setBeforBalance(userAccount.getBalance());
        if(dto.getIsIncome()){
            userAccount.setBalance(userAccount.getBalance().add(dto.getAmount()));
        }else {
            if(userAccount.getBalance().compareTo(dto.getAmount())<0){
                ErrorUtils.err(ApiResultEnum.ACCOUNT_INSUFFICIENT_BALANCE);
            }
            userAccount.setBalance(userAccount.getBalance().subtract(dto.getAmount()));
        }
        if(this.updateById(userAccount)){
            // 成功更新余额，添加账户明细表
            userAccountDetail.setAfterBalance(userAccount.getBalance())
                    .setUserId(dto.getUserId())
                    .setAmount(dto.getAmount())
                    .setIncome(dto.getIsIncome())
                    .setSource(dto.getSource())
                    .setOrderNumber(dto.getOrderNumber());
            return userAccountDetailService.save(userAccountDetail);
        }else{
            ErrorUtils.err(ApiResultEnum.ACCOUNT_UPDATE_ERROR);
        }
        return false;
    }
}
