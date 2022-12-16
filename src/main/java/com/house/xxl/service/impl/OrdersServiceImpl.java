package com.house.xxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.xxl.common.Result;
import com.house.xxl.config.CustomException;
import com.house.xxl.dao.*;
import com.house.xxl.dto.OrderDTO;
import com.house.xxl.dto.OrderProductDTO;
import com.house.xxl.model.*;
import com.house.xxl.service.Ordersservice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements Ordersservice {

    @Resource
    private AlcoholsMapper alcoholsMapper;

    @Resource
    private AlcoholsSkuMapper alcoholsSkuMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public Result addUserOrder(OrderDTO orderDTO, String userId) {

        Long orderId = System.currentTimeMillis() / 10 / 1000;//生成十位订单的id
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
        //插入订单多条记录
        List<OrderProductDTO> dtos = orderDTO.getShopCart();
        OrderItem orderItem = new OrderItem();
        dtos.forEach(item -> {
            Alcohols alco = alcoholsMapper.selectOne(new QueryWrapper<Alcohols>().eq("alco_id", item.getAlcoId()));
            AlcoholsSku sku = null;
            if (item.getSkuId().equals("1")) {
                sku = alcoholsSkuMapper.selectOne(new QueryWrapper<AlcoholsSku>().eq("alco_id", item.getAlcoId()).eq("sku_cap", "一瓶"));
            } else {
                sku = alcoholsSkuMapper.selectOne(new QueryWrapper<AlcoholsSku>().eq("alco_id", item.getAlcoId()).eq("sku_cap", "一杯"));
            }
            if (sku.getSkuStock() - item.getNum() < 0) {
                throw new CustomException(-1, "库存不足");
            }
            AlcoholsSku sku1 = alcoholsSkuMapper.selectOne(new QueryWrapper<AlcoholsSku>().eq("alco_id", item.getAlcoId()).eq("sku_cap", "一瓶"));
            orderItem.setOrderId(orderId);
            orderItem.setAcloPrice(sku.getSkuSellprice());
            orderItem.setAlcoName(alco.getName());
            orderItem.setAlcoImage(alco.getImgUrl());
            orderItem.setDeposit("否");
            orderItem.setBuyNum(item.getNum());
            orderItem.setAlcoSku(sku.getSkuCap());
            orderItem.setBuyNum(item.getNum());
            orderItem.setCreateTime(LocalDateTime.now());
            orderItem.setUpdateTime(LocalDateTime.now());
            orderItemMapper.insert(orderItem);
            if (item.getSkuId().equals("2") && (sku.getSkuStock() < item.getNum())) {
                //扣减完原本库存还需要的杯数
                int i = item.getNum() - sku.getSkuStock();
                //是否一瓶可以解决
                int j = alco.getAlcoCap() - (i * 50);
                if (j > 0) {
                    sku.setSkuStock(j / 50);
                    alcoholsSkuMapper.updateById(sku);
                    sku1.setSkuStock(sku1.getSkuStock() - 1);
                    alcoholsSkuMapper.updateById(sku1);
                } else {
                    //多瓶才可以
                    //扣减完需要的瓶数
                    int s = (i * 50) / 700 + 1;
                    //需要增加的杯数
                    int t = (s * 14) - i;
                    sku.setSkuStock(t);
                    alcoholsSkuMapper.updateById(sku);
                    sku1.setSkuStock(sku1.getSkuStock() - s);
                    alcoholsSkuMapper.updateById(sku1);
                }
            } else {
                //减少商品的库存的数量

                if (sku.getSkuStock() - item.getNum() > 0) {
                    sku.setSkuStock(sku.getSkuStock() - item.getNum());
                }


                alcoholsSkuMapper.updateById(sku);
            }
        });

        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(String.valueOf(user.getId()));
        orders.setTotal(orderDTO.getTotal());
        orders.setPhone(user.getPhone());
        orders.setStatus("未支付");
        orders.setCount(orderDTO.getCount());
        orders.setCreateTime(LocalDateTime.now());
        orders.setUpdateTime(LocalDateTime.now());
        ordersMapper.insert(orders);
        return Result.success(orderId);
    }

}
