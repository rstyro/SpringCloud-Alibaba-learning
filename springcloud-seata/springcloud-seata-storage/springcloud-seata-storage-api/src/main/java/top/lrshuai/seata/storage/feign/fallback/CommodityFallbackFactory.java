package top.lrshuai.seata.storage.feign.fallback;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.storage.commons.dto.UpdateCommodityDto;
import top.lrshuai.seata.storage.feign.ICommodityFeign;

@Component
public class CommodityFallbackFactory implements FallbackFactory<ICommodityFeign> {

    @Override
    public ICommodityFeign create(Throwable throwable) {
        return new ICommodityFeign() {
            @Override
            public Result getAccountInfo(Long userId) {
                return Result.error(ApiResultEnum.FEIGN_ERROR);
            }

            @Override
            public Result operateAccount(UpdateCommodityDto dto) {
                return Result.error(ApiResultEnum.FEIGN_ERROR);
            }
        };
    }
}
