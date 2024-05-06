package top.lrshuai.mall.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.common.core.enums.ApiResultEnum;
import top.lrshuai.common.core.utils.ErrorUtils;
import top.lrshuai.mall.api.dto.UpdateCommodityDto;
import top.lrshuai.mall.api.entity.Commodity;
import top.lrshuai.mall.mapper.CommodityMapper;
import top.lrshuai.mall.service.ICommodityService;

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
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    @Override
    public Commodity getCommodityByCode(String commodityCode) {
        log.info("commodityCode={}",commodityCode);
        return this.getOne(new LambdaQueryWrapper<Commodity>().eq(Commodity::getCommodityCode,commodityCode));
    }

    @Transactional
    public boolean updateCommodityStock(UpdateCommodityDto dto) {
        log.info("dto={}", JSON.toJSONString(dto));
        Commodity commodity = getCommodityByCode(dto.getCode());
        if(commodity==null){
            ErrorUtils.err(ApiResultEnum.MALL_GOODS_NOT_FOUND);
        }
        int result = commodity.getCount() - dto.getDecrCount();
        if(result<0){
            ErrorUtils.err(ApiResultEnum.STORAGE_INSUFFICIENT_COUNT);
        }
        commodity.setCount(result);
        return this.updateById(commodity);
    }
}
