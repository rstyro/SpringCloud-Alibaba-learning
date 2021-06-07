package top.lrshuai.seata.storage.feign.fallback;


import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.seata.storage.commons.dto.UpdateCommodityDto;
import top.lrshuai.seata.storage.commons.entity.Commodity;
import top.lrshuai.seata.storage.feign.ICommodityFeign;

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
