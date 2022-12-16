package com.house.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author auther: xxl
 * @since 2022-10-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Store extends Model<Store> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String addr;

    private String phone;

    private String status;

    @TableField(exist = false)
    private Boolean isOpen;


    @Override

    protected Serializable pkVal() {
        return this.id;
    }

}
