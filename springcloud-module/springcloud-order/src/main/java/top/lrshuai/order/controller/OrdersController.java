package top.lrshuai.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.web.BaseController;
import top.lrshuai.common.security.annotation.LoginIgnore;
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
@Tag(name = "订单相关",description = "订单相关")
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

    @GetMapping(path = "/getMessageSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @LoginIgnore
    public SseEmitter handleSse() {
        SseEmitter emitter = new SseEmitter();
        // 在新线程中发送事件以避免阻塞主线程
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    emitter.send("data:" + i +System.lineSeparator()+System.lineSeparator()); // 发送数据
                    Thread.sleep(1000); // 每秒发送一次
                }
                emitter.complete(); // 完成发送
            } catch (Exception e) {
                emitter.completeWithError(e); // 发送错误
            }
        }).start();
        return emitter;
    }
}
