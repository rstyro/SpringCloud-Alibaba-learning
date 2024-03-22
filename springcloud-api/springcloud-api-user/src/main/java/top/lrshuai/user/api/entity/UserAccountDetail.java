package top.lrshuai.user.api.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2024-03-15
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user_account_detail")
@Schema(name = "UserAccountDetail", description = "")
public class UserAccountDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @Schema(description = "操作前的余额")
    private BigDecimal beforBalance;

    @Schema(description = "操作后的余额")
    private BigDecimal afterBalance;

    @Schema(description = "操作的金额")
    private BigDecimal amount;

    @Schema(description = "收入或支出类型：1--购物，2---转账")
    private Integer source;

    @Schema(description = "订单号")
    private String orderNumber;

    @Schema(description = "是否是收入：1--是收入，0--支出")
    private Boolean income;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
