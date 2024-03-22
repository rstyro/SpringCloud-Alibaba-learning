package top.lrshuai.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.mall.api.dto.UpdateCommodityDto;
import top.lrshuai.mall.api.entity.Commodity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2024-03-19
 */
public interface ICommodityService extends IService<Commodity> {
    Commodity getCommodityByCode(String commodityCode);
    boolean updateCommodityStock(UpdateCommodityDto dto);
}
