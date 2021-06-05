package top.lrshuai.seata.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.utils.ErrorUtils;
import top.lrshuai.seata.feign.IUserAccountFeign;
import top.lrshuai.seata.feign.IUserFeign;
import top.lrshuai.seata.order.dto.PayDto;
import top.lrshuai.seata.order.entity.Orders;
import top.lrshuai.seata.order.mapper.OrdersMapper;
import top.lrshuai.seata.order.service.IOrdersService;
import top.lrshuai.seata.user.commons.dto.UpdateAccountDto;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {


    private IUserFeign userFeign;
    private IUserAccountFeign userAccountFeign;

    @Autowired
    public void setUserFeign(IUserFeign userFeign) {
        this.userFeign = userFeign;
    }

    @Autowired
    public void setUserAccountFeign(IUserAccountFeign userAccountFeign) {
        this.userAccountFeign = userAccountFeign;
    }

    @Override
    @Transactional
    @GlobalTransactional(rollbackFor = Exception.class)
    public String payOrder(PayDto dto) {
        Orders orders = new Orders().setCommoditysCode(dto.getCommodityCode())
                .setCount(dto.getCount()).setUserId(dto.getUserId()).setMoney(new BigDecimal("10000")).setOderNumber("abc1001");
        this.save(orders);
        Result result = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(dto.getIn()).setIsIncome(Boolean.TRUE).setSource(1).setUserId(dto.getUserId()));
        if(!result.get("status").toString().equals("200")){
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Result result1 = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(dto.getOut()).setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()));
        if(!result1.get("status").toString().equals("200")){
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Result userInfo = userFeign.getUserInfo(dto.getUserId());
        System.out.println("result="+result.toString());
        System.out.println("result1="+result1.toString());
        System.out.println("userInfo="+userInfo.toString());
        return orders.getOderNumber();
//        return "ok";
    }
}
