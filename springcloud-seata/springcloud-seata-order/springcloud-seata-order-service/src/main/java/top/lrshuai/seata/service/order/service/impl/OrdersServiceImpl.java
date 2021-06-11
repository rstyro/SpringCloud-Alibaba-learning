package top.lrshuai.seata.service.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.RemoteResult;
import top.lrshuai.nacos.commons.Result;
import top.lrshuai.nacos.commons.utils.ErrorUtils;
import top.lrshuai.nacos.commons.utils.IdUtil;
import top.lrshuai.seata.commons.order.dto.PayDto;
import top.lrshuai.seata.commons.order.entity.Orders;
import top.lrshuai.seata.commons.storage.dto.UpdateCommodityDto;
import top.lrshuai.seata.commons.storage.entity.Commodity;
import top.lrshuai.seata.commons.user.dto.UpdateAccountDto;
import top.lrshuai.seata.feign.storage.ICommodityFeign;
import top.lrshuai.seata.feign.user.IUserAccountFeign;
import top.lrshuai.seata.service.order.mapper.OrdersMapper;
import top.lrshuai.seata.service.order.service.IOrdersService;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Service
@Slf4j
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
    public Result payOrder(PayDto dto) {
        log.info("全局事务xid={}", RootContext.getXID());
        // 获取商品信息
        RemoteResult<Commodity> commodityInfo = commodityFeign.getInfoByCode(dto.getCommodityCode());
        if (!commodityInfo.ok()) {
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Commodity commodity = commodityInfo.getData();
        String orderNumber = IdUtil.getRandomUUID();
        // 总金额
        BigDecimal totalPay = commodity.getMoney().multiply(new BigDecimal(dto.getCount().toString()));
        // 扣除余额
        RemoteResult result1 = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(totalPay)
                .setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()).setOrderNumber(orderNumber));
        if (!result1.ok()) {
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        // 扣除库存
        RemoteResult<Boolean> update = commodityFeign.update(new UpdateCommodityDto().setCode(dto.getCommodityCode()).setDecrCount(dto.getCount()));
        if (!update.ok() && !update.getData()) {
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Orders orders = new Orders().setCommoditysCode(dto.getCommodityCode())
                .setCount(dto.getCount()).setUserId(dto.getUserId())
                .setMoney(totalPay).setOderNumber(orderNumber);
        // 新增订单
        this.save(orders);
        return Result.ok(orders);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public Result payOrder2(PayDto dto) throws TransactionException {
        try{
            log.info("全局事务xid={}", RootContext.getXID());
            // 获取商品信息
            RemoteResult<Commodity> commodityInfo = commodityFeign.getInfoByCode(dto.getCommodityCode());
            if (!commodityInfo.ok()) {
                // 这个方法可以回滚全局事务
                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                return Result.error(ApiResultEnum.FEIGN_ERROR);
            }
            Commodity commodity = commodityInfo.getData();
            // 总金额
            BigDecimal totalPay = commodity.getMoney().multiply(new BigDecimal(dto.getCount().toString()));
            // 扣除余额
            RemoteResult result1 = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(totalPay).setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()));
            if (!result1.ok()) {
                // 这个方法可以回滚全局事务
                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                return Result.error(ApiResultEnum.FEIGN_ERROR);
            }
            // 扣除库存
            RemoteResult<Boolean> update = commodityFeign.update(new UpdateCommodityDto().setCode(dto.getCommodityCode()).setDecrCount(dto.getCount()));
            if (!update.ok() && !update.getData()) {
                // 这个方法可以回滚全局事务
                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                return Result.error(ApiResultEnum.FEIGN_ERROR);
            }
            Orders orders = new Orders().setCommoditysCode(dto.getCommodityCode())
                    .setCount(dto.getCount()).setUserId(dto.getUserId())
                    .setMoney(totalPay).setOderNumber(IdUtil.getRandomUUID());
            // 新增订单
            this.save(orders);
            return Result.ok(orders);
        }catch (Exception e){
            // 这个方法可以回滚全局事务
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
            return Result.error(ApiResultEnum.FEIGN_ERROR);
        }
    }

    @Override
    public Result payOrder3(PayDto dto) {
        log.info("全局事务xid={}", RootContext.getXID());
        // 获取商品信息
        RemoteResult<Commodity> commodityInfo = commodityFeign.getInfoByCode(dto.getCommodityCode());
        if (!commodityInfo.ok()) {
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Commodity commodity = commodityInfo.getData();
        // 总金额
        BigDecimal totalPay = commodity.getMoney().multiply(new BigDecimal(dto.getCount().toString()));
        // 扣除余额
        RemoteResult result1 = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(totalPay).setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()));
        if (!result1.ok()) {
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        /**
         * 扣除库存这里不走全局事务，如果失败，照样进行
         */
        // 解绑全局事务ID,不受全局事务影响，报错也不会回滚
        String unbind = RootContext.unbind();
        try{
            // 扣除库存
            RemoteResult<Boolean> update = commodityFeign.update(new UpdateCommodityDto().setCode(dto.getCommodityCode()).setDecrCount(dto.getCount()));
            if (!update.ok() && !update.getData()) {
                ErrorUtils.err(ApiResultEnum.ERROR);
            }
        }catch (Exception e){
            log.error("扣除库存失败，商品编号="+dto.getCommodityCode(),e);
        }finally {
            if(!StringUtils.isEmpty(unbind)){
                // 重新绑定全局事务，下面全局事务会生效
                RootContext.bind(unbind);
            }
        }
        Orders orders = new Orders().setCommoditysCode(dto.getCommodityCode())
                .setCount(dto.getCount()).setUserId(dto.getUserId())
                .setMoney(totalPay).setOderNumber(IdUtil.getRandomUUID());
        // 新增订单
        this.save(orders);
        return null;
    }
}
