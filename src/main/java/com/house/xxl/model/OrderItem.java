package com.house.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderItem extends Model<OrderItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;


    /**
     * 价格
     */
    private BigDecimal acloPrice;

    /**
     * 酒名
     */
    private String alcoName;

    /**
     * 酒的规格
     */
    private String alcoSku;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 购买数量
     */
    private Integer buyNum;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;

    private String alcoImage;

    private String deposit;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
