package top.lrshuai.mall.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.security.annotation.LoginIgnore;
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
