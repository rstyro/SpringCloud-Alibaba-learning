package top.lrshuai.seata.feign.storage.fallback;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.seata.feign.storage.ICommodityFeign;
import top.lrshuai.seata.commons.storage.dto.UpdateCommodityDto;
import top.lrshuai.seata.commons.storage.entity.Commodity;

@Component
public class CommodityFallbackFactory implements FallbackFactory<ICommodityFeign> {

    @Override
    public ICommodityFeign create(Throwable throwable) {
        return new ICommodityFeign() {
            @Override
            public RemoteResult<Commodity> getInfoByCode(String code) {
                return RemoteResult.error(ApiResultEnum.FEIGN_ERROR);
            }

            @Override
            public RemoteResult<Boolean> update(UpdateCommodityDto dto) {
                return RemoteResult.error(ApiResultEnum.FEIGN_ERROR);
            }
        };
    }
}
