package top.lrshuai.seata.commons.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author rstyro
 * @since 2021-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 商品编码
     */
    private String commodityCode;

    /**
     * 库存
     */
    private Integer count;

    /**
     * 价格
     */
    private BigDecimal money;

    private LocalDateTime createTime;


}
