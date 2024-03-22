package top.lrshuai.mall.api.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
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
 * @since 2024-03-19
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "Commodity", description = "")
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "商品名称")
    private String commodityName;

    @Schema(description = "商品编码")
    private String commodityCode;

    @Schema(description = "库存")
    private Integer count;

    @Schema(description = "价格")
    private BigDecimal money;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
