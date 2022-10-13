package com.house.xxl.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class Alcohols extends Model<Alcohols> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "alco_id", type = IdType.INPUT)
    private Long alcoId;

    /**
     * 酒的编号
     */
    private String alcoNumber;

    /**
     * 酒的名字
     */
    private String alcoName;

    /**
     * 酒的图片
     */
    private String alcoImg;

    /**
     * 产地
     */
    private String alcoOrigin;

    /**
     * 供应商
     */
    private String alcoSupplier;

    /**
     * 酒的类别
     */
    private String alcoType;

    /**
     * 酒的宣言
     */
    private String alcoDec;

    /**
     * 酒的利润百分比
     */
    private String alcoProfit;

    /**
     * 酒的容量
     */
    private Integer alcoCap;

    /**
     * 酒的进价
     */
    private BigDecimal alcoOriginprice;

    /**
     * 酒的状态,0正常，1下架，2删除
     */
    private String alcoStatus;

    /**
     * 酒的备注
     */
    private String alcoRemarks;

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


    @TableField(exist = false)
    private List<AlcoholsSku> alcoholsSku;

    @TableField(exist = false)
    private List<AlcoholsSku> alcoholsSkuList;


    @Override

    protected Serializable pkVal() {
        return this.alcoId;
    }

}
