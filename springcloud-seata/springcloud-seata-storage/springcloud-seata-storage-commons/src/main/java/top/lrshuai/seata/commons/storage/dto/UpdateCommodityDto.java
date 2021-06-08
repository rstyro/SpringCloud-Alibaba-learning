package top.lrshuai.seata.commons.storage.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class UpdateCommodityDto {
    @NotBlank(message = "code不能为空")
    private String code;

    @NegativeOrZero(message = "amount不能小于0")
    private Integer decrCount;
}
