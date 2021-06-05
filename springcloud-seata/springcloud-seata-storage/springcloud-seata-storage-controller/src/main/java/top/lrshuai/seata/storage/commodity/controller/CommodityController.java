package top.lrshuai.seata.storage.commodity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.nacos.commons.Result;
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
@RequestMapping("/commodity")
public class CommodityController {

    private ICommodityService commodityService;

    @Autowired
    public void setCommodityService(ICommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping("/info/{code}")
    public Result getUserInfo(@PathVariable String code){
        return Result.ok(commodityService.getCommodityByCode(code));
    }
}
