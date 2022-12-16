package com.house.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Desk extends Model<Desk> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String deskNo;

    /**
     * 0空闲，1有人，2损坏
     */
    private String deskStatus;


    @TableField(exist = false)
    private List<Desk> deskList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
