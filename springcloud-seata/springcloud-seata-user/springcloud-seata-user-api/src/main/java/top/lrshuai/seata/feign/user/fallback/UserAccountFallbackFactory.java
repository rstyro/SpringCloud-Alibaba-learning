package top.lrshuai.seata.feign.user.fallback;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.seata.feign.user.IUserAccountFeign;
import top.lrshuai.seata.commons.user.dto.UpdateAccountDto;

@Component
public class UserAccountFallbackFactory implements FallbackFactory<IUserAccountFeign> {
    @Override
    public IUserAccountFeign create(Throwable throwable) {
        return new IUserAccountFeign() {
            @Override
            public RemoteResult getAccountInfo(Long userId) {
                return RemoteResult.error(ApiResultEnum.FEIGN_ERROR);
            }

            @Override
            public RemoteResult operateAccount(UpdateAccountDto dto) {
                return RemoteResult.error(ApiResultEnum.FEIGN_ERROR);
            }
        };
    }
}
