package top.lrshuai.seata.storage.commodity.feign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.seata.commons.storage.dto.UpdateCommodityDto;
import top.lrshuai.seata.commons.storage.entity.Commodity;
import top.lrshuai.seata.service.storage.service.ICommodityService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@RestController
@RequestMapping("/feign/commodity")
public class CommodityFeignController{

    private ICommodityService commodityService;

    @Autowired
    public void setCommodityService(ICommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping("/info/{code}")
    public RemoteResult<Commodity> get(@PathVariable String code){
        return RemoteResult.data(commodityService.getCommodityByCode(code));
    }

    @PostMapping("/update")
    public RemoteResult<Boolean> update(@RequestBody UpdateCommodityDto dto){
        return RemoteResult.data(commodityService.updateCommodityStock(dto));
    }
}
