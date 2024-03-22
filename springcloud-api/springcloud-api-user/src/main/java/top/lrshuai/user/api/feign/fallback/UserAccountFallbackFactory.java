package top.lrshuai.user.api.feign.fallback;


import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.user.api.dto.UpdateAccountDto;
import top.lrshuai.user.api.feign.IUserAccountFeign;

@Component
public class UserAccountFallbackFactory implements FallbackFactory<IUserAccountFeign> {
    @Override
    public IUserAccountFeign create(Throwable throwable) {
        return new IUserAccountFeign() {
            @Override
            public R getAccountInfo(Long userId) {
                return R.fail(ApiResultEnum.FEIGN_ERROR);
            }

            @Override
            public R operateAccount(UpdateAccountDto dto) {
                return R.fail(ApiResultEnum.FEIGN_ERROR);
            }
        };
    }
}
