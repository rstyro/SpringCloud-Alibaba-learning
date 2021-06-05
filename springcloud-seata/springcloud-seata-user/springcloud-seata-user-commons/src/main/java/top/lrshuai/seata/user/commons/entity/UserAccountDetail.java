package top.lrshuai.seata.user.commons.entity;

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
public class UserAccountDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 操作前的余额
     */
    private BigDecimal beforBalance;

    /**
     * 操作后的余额
     */
    private BigDecimal afterBalance;

    /**
     * 操作的金额
     */
    private BigDecimal amount;

    /**
     * 收入或支出类型：1--购物，2---转账
     */
    private Integer source;

    /**
     * 是否是收入：1--是收入，0--支出
     */
    private Boolean income;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;


}
