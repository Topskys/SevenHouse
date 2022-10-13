package com.house.xxl.service.impl;

import com.house.xxl.model.OrderItem;
import com.house.xxl.dao.OrderItemMapper;
import com.house.xxl.service.OrderItemservice;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemservice {

}
