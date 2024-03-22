package top.lrshuai.user.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class UpdateAccountDto implements Serializable {

    @NotEmpty(message = "userId不能为空")
    private Long userId;

    @NotEmpty(message = "amount不能为空")
    @NegativeOrZero(message = "amount不能小于0")
    private BigDecimal amount;

    private Boolean isIncome;

    @NotEmpty(message = "source不能为空")
    private Integer source;

    private String orderNumber;
}
