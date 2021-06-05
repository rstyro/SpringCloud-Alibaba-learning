package top.lrshuai.seata.storage.service.commodity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lrshuai.nacos.commons.ApiResultEnum;
import top.lrshuai.nacos.commons.utils.ErrorUtils;
import top.lrshuai.seata.storage.commons.dto.UpdateCommodityDto;
import top.lrshuai.seata.storage.commons.entity.Commodity;
import top.lrshuai.seata.storage.service.commodity.mapper.CommodityMapper;
import top.lrshuai.seata.storage.service.commodity.service.ICommodityService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    @Override
    public Commodity getCommodityByCode(String commodityCode) {
        return this.getOne(new LambdaQueryWrapper<Commodity>().eq(Commodity::getCommodityCode,commodityCode));
    }

    @Transactional
    public boolean updateCommodityStock(UpdateCommodityDto dto) {
        Commodity commodity = getCommodityByCode(dto.getCode());
        if(commodity!=null){
            int result = commodity.getCount() - dto.getDecrCount();
            if(result<0){
                ErrorUtils.err(ApiResultEnum.STORAGE_INSUFFICIENT_COUNT);
            }
            commodity.setCount(result);
            return this.updateById(commodity);
        }
        return false;
    }
}
