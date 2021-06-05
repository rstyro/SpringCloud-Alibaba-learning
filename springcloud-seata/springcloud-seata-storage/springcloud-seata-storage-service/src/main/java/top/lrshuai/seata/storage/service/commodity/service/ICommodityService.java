package top.lrshuai.seata.storage.service.commodity.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.seata.storage.commons.dto.UpdateCommodityDto;
import top.lrshuai.seata.storage.commons.entity.Commodity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
public interface ICommodityService extends IService<Commodity> {
    Commodity getCommodityByCode(String commodityCode);
    boolean updateCommodityStock(UpdateCommodityDto dto);
}
