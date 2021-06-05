package top.lrshuai.seata.feign.fallback;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.feign.IUserAccountFeign;
import top.lrshuai.seata.user.commons.dto.UpdateAccountDto;

@Component
public class UserAccountFallbackFactory implements FallbackFactory<IUserAccountFeign> {
    @Override
    public IUserAccountFeign create(Throwable throwable) {
        return new IUserAccountFeign() {
            @Override
            public Result getAccountInfo(Long userId) {
                return Result.error(ApiResultEnum.FEIGN_ERROR);
            }

            @Override
            public Result operateAccount(UpdateAccountDto dto) {
                return Result.error(ApiResultEnum.FEIGN_ERROR);
            }
        };
    }
}
