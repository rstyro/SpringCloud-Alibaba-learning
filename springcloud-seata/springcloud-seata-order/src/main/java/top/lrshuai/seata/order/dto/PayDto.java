package top.lrshuai.seata.order.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class PayDto implements Serializable {
    private String commodityCode;
    private Integer count;
    private Long userId;
    private BigDecimal in;
    private BigDecimal out;
}
