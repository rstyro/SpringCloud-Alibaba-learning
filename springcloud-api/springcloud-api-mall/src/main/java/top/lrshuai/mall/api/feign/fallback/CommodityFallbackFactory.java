package top.lrshuai.mall.api.feign.fallback;


import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.mall.api.dto.UpdateCommodityDto;
import top.lrshuai.mall.api.entity.Commodity;
import top.lrshuai.mall.api.feign.ICommodityFeign;

@Component
public class CommodityFallbackFactory implements FallbackFactory<ICommodityFeign> {

    @Override
    public ICommodityFeign create(Throwable throwable) {
        return new ICommodityFeign() {
            @Override
            public R<Commodity> getInfoByCode(String code) {
                return R.fail(ApiResultEnum.FEIGN_ERROR);
            }

            @Override
            public R<Boolean> update(UpdateCommodityDto dto) {
                return R.fail(ApiResultEnum.FEIGN_ERROR);
            }
        };
    }
}
