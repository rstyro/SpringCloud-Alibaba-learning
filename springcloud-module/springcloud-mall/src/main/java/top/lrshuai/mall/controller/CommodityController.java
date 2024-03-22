package top.lrshuai.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.mall.service.ICommodityService;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2024-03-19
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Resource
    private ICommodityService commodityService;

    @GetMapping("/info/{code}")
    public R getUserInfo(@PathVariable String code){
        return R.ok(commodityService.getCommodityByCode(code));
    }
}
