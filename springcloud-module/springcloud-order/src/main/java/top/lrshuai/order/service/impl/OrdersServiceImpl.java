package top.lrshuai.order.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.resp.R;
import top.lrshuai.common.core.utils.ErrorUtils;
import top.lrshuai.mall.api.dto.UpdateCommodityDto;
import top.lrshuai.mall.api.entity.Commodity;
import top.lrshuai.mall.api.feign.ICommodityFeign;
import top.lrshuai.order.api.dto.PayDto;
import top.lrshuai.order.api.entity.Orders;
import top.lrshuai.order.mapper.OrdersMapper;
import top.lrshuai.order.service.IOrdersService;
import top.lrshuai.user.api.dto.UpdateAccountDto;
import top.lrshuai.user.api.feign.IUserAccountFeign;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2024-03-19
 */
@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    @Resource
    private IUserAccountFeign userAccountFeign;

    @Resource
    private ICommodityFeign commodityFeign;


    @Override
    @Transactional
    @GlobalTransactional(rollbackFor = Exception.class)
    public R payOrder(PayDto dto) {
        log.info("全局事务xid={}", RootContext.getXID());
        // 获取商品信息
        R<Commodity> commodityInfo = commodityFeign.getInfoByCode(dto.getCommodityCode());
        if (!commodityInfo.isSuccess()) {
            // 主动报错，触发回滚
            ErrorUtils.err(ApiResultEnum.ERROR);
        }
        Commodity commodity = commodityInfo.getData();
        String orderNumber = IdUtil.getSnowflakeNextIdStr();
        // 总金额
        BigDecimal totalPay = commodity.getMoney().multiply(new BigDecimal(dto.getCount().toString()));
        // 扣除余额
        R result1 = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(totalPay)
                .setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()).setOrderNumber(orderNumber));
        if (!result1.isSuccess()) {
            ErrorUtils.err(result1.getCode(),result1.getMsg());
        }
        // 扣除库存
        R<Boolean> update = commodityFeign.update(new UpdateCommodityDto().setCode(dto.getCommodityCode()).setDecrCount(dto.getCount()));
        if (!update.isSuccess()) {
            ErrorUtils.err(update.getCode(),update.getMsg());
        }
        Orders orders = new Orders().setCommoditysCode(dto.getCommodityCode())
                .setCount(dto.getCount()).setUserId(dto.getUserId())
                .setMoney(totalPay).setOderNumber(orderNumber);
        // 新增订单
        this.save(orders);
        return R.ok(orders);
    }

    @SneakyThrows
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public R payOrder2(PayDto dto) {
        try{
            log.info("全局事务xid={}", RootContext.getXID());
            // 获取商品信息
            R<Commodity> commodityInfo = commodityFeign.getInfoByCode(dto.getCommodityCode());
            if (!commodityInfo.isSuccess()) {
                // 这个方法可以回滚全局事务
                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                ErrorUtils.err(commodityInfo.getCode(),commodityInfo.getMsg());
            }
            Commodity commodity = commodityInfo.getData();
            // 总金额
            BigDecimal totalPay = commodity.getMoney().multiply(new BigDecimal(dto.getCount().toString()));
            // 扣除余额
            R<Boolean> result = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(totalPay).setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()));
            if (!result.isSuccess()) {
                // 这个方法可以回滚全局事务
                if(!ObjUtil.isEmpty(RootContext.getXID())){
                    GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                }
                return R.fail(ApiResultEnum.FEIGN_ERROR);
            }
            // 扣除库存
            R<Boolean> update = commodityFeign.update(new UpdateCommodityDto().setCode(dto.getCommodityCode()).setDecrCount(dto.getCount()));
            if (!update.isSuccess()) {
                // 这个方法可以回滚全局事务
                GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                return R.fail(ApiResultEnum.FEIGN_ERROR);
            }
            Orders orders = new Orders().setCommoditysCode(dto.getCommodityCode())
                    .setCount(dto.getCount()).setUserId(dto.getUserId())
                    .setMoney(totalPay).setOderNumber(IdUtil.getSnowflakeNextIdStr());
            // 新增订单
            this.save(orders);
            return R.ok(orders);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            // 这个方法可以回滚全局事务
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }
        return R.fail(ApiResultEnum.FEIGN_ERROR);
    }

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public R payOrder3(PayDto dto) {
        log.info("全局事务xid={}", RootContext.getXID());
        // 获取商品信息
        R<Commodity> commodityInfo = commodityFeign.getInfoByCode(dto.getCommodityCode());
        if (!commodityInfo.isSuccess()) {
            ErrorUtils.err(commodityInfo.getCode(),commodityInfo.getMsg());
        }
        Commodity commodity = commodityInfo.getData();
        // 总金额
        BigDecimal totalPay = commodity.getMoney().multiply(new BigDecimal(dto.getCount().toString()));
        // 扣除余额
        R<Boolean> result1 = userAccountFeign.operateAccount(new UpdateAccountDto().setAmount(totalPay).setIsIncome(Boolean.FALSE).setSource(1).setUserId(dto.getUserId()));
        if (!result1.isSuccess()) {
            ErrorUtils.err(result1.getCode(),result1.getMsg());
        }
        /**
         * 扣除库存这里不走全局事务，如果失败，照样进行
         */
        // 解绑全局事务ID,不受全局事务影响，报错也不会回滚
        String unbind = RootContext.unbind();
        try{
            // 扣除库存
            R<Boolean> update = commodityFeign.update(new UpdateCommodityDto().setCode(dto.getCommodityCode()).setDecrCount(dto.getCount()));
            if (!update.isSuccess()) {
                ErrorUtils.err(update.getCode(),update.getMsg());
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
                .setMoney(totalPay).setOderNumber(IdUtil.getSnowflakeNextIdStr());
        // 新增订单
        this.save(orders);
        return R.ok(orders);
    }
}
