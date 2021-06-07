package top.lrshuai.seata.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.nacos.commons.utils.ErrorUtils;
import top.lrshuai.nacos.commons.utils.IdUtil;
import top.lrshuai.seata.feign.IUserAccountFeign;
import top.lrshuai.seata.order.dto.PayDto;
import top.lrshuai.seata.order.entity.Orders;
import top.lrshuai.seata.order.mapper.OrdersMapper;
import top.lrshuai.seata.order.service.IOrdersService;
import top.lrshuai.seata.storage.commons.dto.UpdateCommodityDto;
import top.lrshuai.seata.storage.commons.entity.Commodity;
import top.lrshuai.seata.storage.feign.ICommodityFeign;
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

    private IUserAccountFeign userAccountFeign;
    private ICommodityFeign commodityFeign;

    @Autowired
    public void setUserAccountFeign(IUserAccountFeign userAccountFeign) {
        this.userAccountFeign = userAccountFeign;
    }

    @Autowired
    public void setCommodityFeign(ICommodityFeign commodityFeign) {
        this.commodityFeign = commodityFeign;
    }

    @Override
    @Transactional
    @GlobalTransactional(rollbackFor = Exception.class)
    public String payOrder(PayDto dto) {
        // 获取商品信息
        RemoteResult<Commodity> commodityInfo = commodityFeign.getInfoByCode(dto.getCommodityCode());
        if(!commodityInfo.ok()){
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Commodity commodity = commodityInfo.getData();
        // 总金额
        BigDecimal totalPay = commodity.getMoney().multiply(new BigDecimal(dto.getCount().toString()));
        // 扣除余额
        RemoteResult result1 = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(totalPay).setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()));
        if(!result1.ok()){
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        // 扣除库存
        RemoteResult<Boolean> update = commodityFeign.update(new UpdateCommodityDto().setCode(dto.getCommodityCode()).setDecrCount(dto.getCount()));
        if(!update.ok() && !update.getData()){
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Orders orders = new Orders().setCommoditysCode(dto.getCommodityCode())
                .setCount(dto.getCount()).setUserId(dto.getUserId())
                .setMoney(totalPay).setOderNumber(IdUtil.getRandomUUID());
        // 新增订单
        this.save(orders);
        return orders.getOderNumber();
    }
}
