package top.lrshuai.mall.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.lrshuai.common.core.constant.ServiceName;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.mall.api.dto.UpdateCommodityDto;
import top.lrshuai.mall.api.entity.Commodity;
import top.lrshuai.mall.api.feign.fallback.CommodityFallbackFactory;


@FeignClient(name = ServiceName.MALL,fallbackFactory = CommodityFallbackFactory.class)
@Component
public interface ICommodityFeign {

    @GetMapping("/feign/commodity/info/{code}")
    R<Commodity> getInfoByCode(@PathVariable("code") String code);

    @PostMapping("/feign/commodity/update")
    R<Boolean> update(UpdateCommodityDto dto);
}
