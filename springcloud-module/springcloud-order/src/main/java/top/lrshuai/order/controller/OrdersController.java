package top.lrshuai.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.web.BaseController;
import top.lrshuai.common.security.annotation.RepeatSubmit;
import top.lrshuai.order.api.dto.PayDto;
import top.lrshuai.order.service.IOrdersService;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2024-03-19
 */
@Tag(name = "order",description = "订单相关")
@RestController
@RequestMapping("/order")
public class OrdersController extends BaseController {

    @Resource
    private IOrdersService ordersService;


    @Operation(summary = "下单测试接口1")
    @PostMapping("/pay1")
    @RepeatSubmit(interval = 9000)
    public R pay1(@RequestBody @Valid PayDto dto){
        return ordersService.payOrder(dto);
    }

    @Operation(summary = "下单测试接口2")
    @PostMapping("/pay2")
    public R pay2(@RequestBody @Valid PayDto dto){
        return ordersService.payOrder2(dto);
    }

    @Operation(summary = "下单测试接口3",description = "seata中途解绑业务报错不影响全局事务，解绑后可再绑定")
    @PostMapping("/pay3")
    public R pay3(@RequestBody @Valid PayDto dto){
        return ordersService.payOrder3(dto);
    }

}
