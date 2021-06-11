package top.lrshuai.seata.commons.order.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class PayDto implements Serializable {
    @NotEmpty(message = "commodityCode不能为空")
    private String commodityCode;
    @NotNull(message = "数量不能为空")
    private Integer count;
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    private BigDecimal in;
    private BigDecimal out;
}
