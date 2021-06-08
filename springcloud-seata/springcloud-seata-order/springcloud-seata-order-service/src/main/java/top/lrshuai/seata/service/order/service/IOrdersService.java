package top.lrshuai.seata.service.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
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
    String payOrder(PayDto dto);
}
