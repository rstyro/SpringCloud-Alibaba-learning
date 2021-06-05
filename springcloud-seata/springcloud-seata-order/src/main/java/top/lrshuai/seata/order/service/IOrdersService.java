package top.lrshuai.seata.order.service;

import top.lrshuai.nacos.commons.Result;
import top.lrshuai.seata.order.dto.PayDto;
import top.lrshuai.seata.order.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

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
