package com.house.xxl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.xxl.model.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
public interface OrdersMapper extends BaseMapper<Orders> {
    Integer getOrderTotal();

    Integer getTodayTotal();

    Integer getOrderCount();

    Integer getMonthTotal(@Param("year") String year, @Param("date") String date);

    List<Map<String, String>> getHot();
}
