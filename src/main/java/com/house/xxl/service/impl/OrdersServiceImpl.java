package com.house.xxl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.xxl.common.Result;
import com.house.xxl.dao.AlcoholsMapper;
import com.house.xxl.dao.AlcoholsSkuMapper;
import com.house.xxl.dao.OrderItemMapper;
import com.house.xxl.dto.OrderDTO;
import com.house.xxl.dto.OrderProductDTO;
import com.house.xxl.model.Alcohols;
import com.house.xxl.model.AlcoholsSku;
import com.house.xxl.model.OrderItem;
import com.house.xxl.model.Orders;
import com.house.xxl.dao.OrdersMapper;
import com.house.xxl.service.Ordersservice;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
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

    @Transactional(rollbackFor = Exception.class)
    public Result addUserOrder(OrderDTO orderDTO) {

        try {
            Orders orders= new Orders();
            List<OrderProductDTO> dtos = orderDTO.getOrderProductDTOS();
            dtos.forEach(item->{
                Alcohols alco = alcoholsMapper.selectOne(new QueryWrapper<Alcohols>().eq("alco_id", item.getAlcoId()));
                String combination = alco.getAlcoName()+",";
                orders.setUserId(orderDTO.getUserId());
                orders.setCombination(combination);
                orders.setTotal(orderDTO.getTotal());
                orders.setDeskId(orderDTO.getDeskId());
                orders.setPhone(orderDTO.getPhone());
                orders.setPayType(orderDTO.getPayType());
                orders.setStatus("0");
                orders.setRemark(orderDTO.getRemark());
                orders.setCreateTime(LocalDateTime.now());
                orders.setUpdateTime(LocalDateTime.now());
            });
            addUserOrder(orders, dtos);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUserOrder(Orders orders,List<OrderProductDTO> dtos){
        ordersMapper.insert(orders);
        OrderItem orderItem = new OrderItem();
        dtos.forEach(item->{
            Alcohols alco = alcoholsMapper.selectOne(new QueryWrapper<Alcohols>().eq("alco_id", item.getAlcoId()));
            AlcoholsSku sku = alcoholsSkuMapper.selectOne(new QueryWrapper<AlcoholsSku>().eq("alco_id", alco.getAlcoId()).eq("sku_cap", item.getSkuName()));
            orderItem.setOrderId(orders.getId());
            orderItem.setAlcoId(item.getAlcoId());
            orderItem.setAcloPrice(sku.getSkuSellprice());
            orderItem.setAlcoName(alco.getAlcoName());
            orderItem.setAlcoSku(sku.getSkuCap());
            orderItem.setBuyNum(item.getBuyNum());
            orderItem.setCreateTime(LocalDateTime.now());
            orderItem.setUpdateTime(LocalDateTime.now());
            orderItemMapper.insert(orderItem);

            if (item.getSkuName().equals("整瓶")){
                if (sku.getSkuStock()-item.getBuyNum()>0){
                    sku.setSkuStock(sku.getSkuStock()-item.getBuyNum());
                }
                alcoholsSkuMapper.updateById(sku);
            }else {
                if (sku.getSkuStock()<item.getBuyNum()){
                    AlcoholsSku sku1 = alcoholsSkuMapper.selectOne(new QueryWrapper<AlcoholsSku>().eq("alco_id", alco.getAlcoId()).eq("sku_cap", "整瓶"));
                    sku1.setSkuStock(sku1.getSkuStock()-1);
                    alcoholsSkuMapper.updateById(sku1);
                    sku.setSkuStock(sku.getSkuStock()+10-item.getBuyNum());
                    alcoholsSkuMapper.updateById(sku);
                }else {
                    sku.setSkuStock(sku.getSkuStock()-item.getBuyNum());
                    alcoholsSkuMapper.updateById(sku);
                }
            }

        });


    }
}
