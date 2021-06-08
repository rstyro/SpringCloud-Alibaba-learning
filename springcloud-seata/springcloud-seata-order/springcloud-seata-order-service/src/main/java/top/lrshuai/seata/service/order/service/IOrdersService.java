package top.lrshuai.seata.service.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.seata.core.exception.TransactionException;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.commons.order.dto.PayDto;
import top.lrshuai.seata.commons.order.entity.Orders;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
public interface IOrdersService extends IService<Orders> {
    /**
     * 下单支付
     */
    Result payOrder(PayDto dto);

    /**
     * 下单支付
     * try-Catch 方式
     */
    Result payOrder2(PayDto dto) throws TransactionException;

    /**
     * 某个服务不走全局事务
     */
    Result payOrder3(PayDto dto);
}
