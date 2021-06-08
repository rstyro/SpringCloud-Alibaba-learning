package top.lrshuai.seata.service.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.utils.ErrorUtils;
import top.lrshuai.seata.service.user.mapper.UserAccountMapper;
import top.lrshuai.seata.service.user.service.IUserAccountDetailService;
import top.lrshuai.seata.service.user.service.IUserAccountService;
import top.lrshuai.seata.commons.user.dto.UpdateAccountDto;
import top.lrshuai.seata.commons.user.entity.UserAccount;
import top.lrshuai.seata.commons.user.entity.UserAccountDetail;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {


    private IUserAccountDetailService userAccountDetailService;

    @Autowired
    public void setUserAccountDetailService(IUserAccountDetailService userAccountDetailService) {
        this.userAccountDetailService = userAccountDetailService;
    }

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
                    .setSource(dto.getSource());
            return userAccountDetailService.save(userAccountDetail);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal("100").compareTo(new BigDecimal("20"))>0);
    }
}
