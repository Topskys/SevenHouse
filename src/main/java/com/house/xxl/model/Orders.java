package com.house.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class Orders extends Model<Orders> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 订单价格
     */
    private Long total;

    /**
     * 桌号
     */
    private String deskId;

    /**
     * 电话
     */
    private String phone;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单备注
     */
    private String remark;


    private String eatType;

    private Integer count;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<OrderItem> orderItemList;

    @TableField(exist = false)
    private String nickName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
