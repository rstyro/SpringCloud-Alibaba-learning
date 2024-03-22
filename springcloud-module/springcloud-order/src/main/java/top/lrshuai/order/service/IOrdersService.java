package top.lrshuai.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.order.api.dto.PayDto;
import top.lrshuai.order.api.entity.Orders;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2024-03-19
 */
public interface IOrdersService extends IService<Orders> {

    /**
     * 下单支付
     */
    R payOrder(PayDto dto);

    /**
     * 下单支付
     * try-Catch 方式
     */
    R payOrder2(PayDto dto);

    /**
     * 某个服务不走全局事务
     */
    R payOrder3(PayDto dto);

}
