package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.xxl.common.Result;
import com.house.xxl.dto.OrderDTO;
import com.house.xxl.model.OrderItem;
import com.house.xxl.model.Orders;
import com.house.xxl.service.OrderItemservice;
import com.house.xxl.service.Ordersservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
@Api(value = "订单接口", tags = "订单相关的接口")
@RestController
@RequestMapping("/orders")
public class OrdersController {


    @Resource
    private Ordersservice ordersService;

    @Resource
    private OrderItemservice orderItemService;


    @ApiOperation(value = "管理员获取用户订单", notes = "type 0 进行中 1 已完成")
    @GetMapping("getUserOrders")
    public Result getUserOrders(@RequestParam("pageNum") Long pageNum,
                                @RequestParam("pageSize") Long pageSize,
                                @RequestParam("type") String type) {
        Page<Orders> ordersPage = ordersService.page(new Page<Orders>(pageNum, pageSize),
                new QueryWrapper<Orders>().eq("status", type).orderByDesc("create_time"));
        List<Orders> records = ordersPage.getRecords();
        //查询子订单项
        records.stream().forEach(item -> {
            List<OrderItem> orderItems = orderItemService.list(new QueryWrapper<OrderItem>().eq("order_id", item.getId()));
            item.setOrderItemList(orderItems);
        });
        return Result.success(ordersPage);
    }

    @ApiOperation(value = "管理员完成用户订单")
    @GetMapping("updateUserOrders")
    public Result updateUserOrders(@RequestParam("orderId") String id) {
        Orders ordersById = ordersService.getById(id);
        if (ordersById == null) {
            return Result.error("id有误");
        }
        if (ordersById.getStatus().equals("1")) {
            return Result.success();
        }
        ordersById.setStatus("1");
        boolean update = ordersService.updateById(ordersById);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation(value = "用户获取自己的个人订单信息列表")
    @GetMapping("getOwnOrders")
    public Result getOwnOrders(@RequestParam("userId") Long userId){
        List<Orders> ordersList = ordersService.list(new QueryWrapper<Orders>().eq("user_id", userId));
        ordersList.forEach(item->{
            List<OrderItem> itemList = orderItemService.list(new QueryWrapper<OrderItem>().eq("order_id", item.getId()));
            item.setOrderItemList(itemList);
        });
        return Result.success(ordersList);
    }

    @ApiOperation(value = "获取订单详情")
    @GetMapping("getOwnOrder")
    public Result getOrderInfo(@RequestParam("orderId") Long orderId){
        Orders orders = ordersService.getOne(new QueryWrapper<Orders>().eq("id", orderId));

        List<OrderItem> itemList = orderItemService.list(new QueryWrapper<OrderItem>().eq("order_id", orders.getId()));
        orders.setOrderItemList(itemList);

        return Result.success(orders);
    }

    @ApiOperation(value = "用户提交订单")
    @PostMapping("addUserOrder")
    public Result addUserOrder(@RequestBody OrderDTO orderDTO){
        return  ordersService.addUserOrder(orderDTO);
    }




}

