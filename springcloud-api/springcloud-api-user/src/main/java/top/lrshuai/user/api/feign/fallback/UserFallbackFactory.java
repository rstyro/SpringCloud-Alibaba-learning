package top.lrshuai.user.api.feign.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.user.api.entity.User;
import top.lrshuai.user.api.feign.IUserFeign;

@Component
public class UserFallbackFactory implements FallbackFactory<IUserFeign> {
    @Override
    public IUserFeign create(Throwable throwable) {
       return new IUserFeign() {
           @Override
           public R<User> getUserInfo(Long userId) {
               return R.fail(ApiResultEnum.FEIGN_ERROR);
           }
       };
    }
}
