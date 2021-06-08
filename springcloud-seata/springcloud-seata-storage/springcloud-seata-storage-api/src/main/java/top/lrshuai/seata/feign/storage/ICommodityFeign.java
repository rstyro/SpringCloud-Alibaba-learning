package top.lrshuai.seata.feign.storage;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.nacos.commons.consts.ServiceName;
import top.lrshuai.seata.commons.storage.dto.UpdateCommodityDto;
import top.lrshuai.seata.commons.storage.entity.Commodity;
import top.lrshuai.seata.feign.storage.fallback.CommodityFallbackFactory;

@FeignClient(name = ServiceName.STORAGE,fallbackFactory = CommodityFallbackFactory.class)
@Component
public interface ICommodityFeign {

    @GetMapping("/feign/commodity/info/{code}")
    RemoteResult<Commodity> getInfoByCode(@PathVariable("code") String code);

    @PostMapping("/feign/commodity/update")
    RemoteResult<Boolean> update(UpdateCommodityDto dto);
}
