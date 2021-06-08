package top.lrshuai.seata.order.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.commons.order.dto.PayDto;
import top.lrshuai.seata.service.order.service.IOrdersService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Api(tags = "订单相关")
@RestController
@RequestMapping("/order/orders")
public class OrdersController {

    private IOrdersService ordersService;
    @Autowired
    public void setOrdersService(IOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @ApiOperation("下单")
    @PostMapping("/pay")
    public Result test(@RequestBody PayDto dto){
        return Result.ok(ordersService.payOrder(dto));
    }
}
