package top.lrshuai.mall.controller.feign;

import org.springframework.web.bind.annotation.*;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.web.BaseController;
import top.lrshuai.mall.api.dto.UpdateCommodityDto;
import top.lrshuai.mall.api.entity.Commodity;
import top.lrshuai.mall.api.feign.ICommodityFeign;
import top.lrshuai.mall.service.ICommodityService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/feign/commodity")
public class CommodityFeignController extends BaseController implements ICommodityFeign {

    @Resource
    private ICommodityService commodityService;

    @GetMapping("/info/{code}")
    public R<Commodity> getInfoByCode(@PathVariable String code){
        return R.ok(commodityService.getCommodityByCode(code));
    }

    @PostMapping("/update")
    @Override
    public R<Boolean> update(@RequestBody UpdateCommodityDto dto) {
        return R.ok(commodityService.updateCommodityStock(dto));
    }
}
