package top.lrshuai.seata.storage.commodity.feign;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.storage.commons.dto.UpdateCommodityDto;
import top.lrshuai.seata.storage.service.commodity.service.ICommodityService;

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
public class CommodityFeignController {

    private ICommodityService commodityService;

    @Autowired
    public void setCommodityService(ICommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping("/info/{code}")
    public Result get(@PathVariable String code){
        return Result.ok(commodityService.getCommodityByCode(code));
    }

    @PostMapping("/update")
    public Result update(@RequestBody UpdateCommodityDto dto){
        return Result.ok(commodityService.updateCommodityStock(dto));
    }
}
