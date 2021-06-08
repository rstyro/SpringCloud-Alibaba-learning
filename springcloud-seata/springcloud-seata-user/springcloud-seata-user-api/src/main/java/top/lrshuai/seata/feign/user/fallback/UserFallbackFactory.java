package top.lrshuai.seata.feign.user.fallback;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.seata.commons.user.entity.Users;
import top.lrshuai.seata.feign.user.IUserFeign;

@Component
public class UserFallbackFactory implements FallbackFactory<IUserFeign> {
    @Override
    public IUserFeign create(Throwable throwable) {
       return new IUserFeign() {
           @Override
           public RemoteResult<Users> getUserInfo(Long userId) {
               return RemoteResult.error(ApiResultEnum.FEIGN_ERROR);
           }
       };
    }
}
