package com.house.xxl.service;

import com.house.xxl.common.Result;
import com.house.xxl.dto.OrderDTO;
import com.house.xxl.model.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
public interface Ordersservice extends IService<Orders> {

    Result addUserOrder(OrderDTO orderDTO);
}
