package com.house.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author auther: xxl
 * @since 2022-11-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepositRecord extends Model<DepositRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 寄存id
     */
    private Long depId;

    /**
     * 酒的数量
     */
    private Integer num;

    /**
     * 状态（0未取出，1已取出）
     */
    private String status;
    private String alcoName;

    private String alcoImg;
    private String userId;
    /**
     * 取酒时间
     */
    private LocalDate createTime;


    @TableField(exist = false)
    private User user;
  

    @Override

    protected Serializable pkVal() {
        return this.id;
    }

}
