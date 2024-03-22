package top.lrshuai.order.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Schema(description = "下单参数实体类")
public class PayDto implements Serializable {
    @NotEmpty(message = "commodityCode不能为空")
    @Schema(description = "商品编号")
    private String commodityCode;

    @NotNull(message = "数量不能为空")
    @Schema(description = "商品数量")
    private Integer count;

    @NotNull(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private Long userId;

    private BigDecimal in;
    private BigDecimal out;
}
