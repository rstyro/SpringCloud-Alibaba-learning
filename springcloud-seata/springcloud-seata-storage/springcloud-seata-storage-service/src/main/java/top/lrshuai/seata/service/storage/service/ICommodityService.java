package top.lrshuai.seata.service.storage.service;


import com.baomidou.mybatisplus.extension.service.IService;
import top.lrshuai.seata.commons.storage.dto.UpdateCommodityDto;
import top.lrshuai.seata.commons.storage.entity.Commodity;

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
