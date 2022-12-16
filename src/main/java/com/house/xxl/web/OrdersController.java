package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.xxl.common.Result;
import com.house.xxl.dto.OrderDTO;
import com.house.xxl.dto.Pay;
import com.house.xxl.model.*;
import com.house.xxl.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
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

    @Resource
    private DepositController depositController;

    @Resource
    private AlcoholsService alcoholsService;
    @Resource
    private Userservice userservice;
    @Autowired
    private DeskController deskController;
    @Resource
    private Deskservice deskservice;


    @ApiOperation(value = "管理员获取用户订单", notes = "type参数：未支付、已支付、完成")
    @GetMapping("getUserOrders")
    public Result getUserOrders(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                @RequestParam(name = "type", required = false) String type,
                                @RequestParam(name = "phone", required = false) String phone) {
        Page<Orders> ordersPage = ordersService.page(new Page<Orders>(pageNum, pageSize),
                new QueryWrapper<Orders>()
                        .eq(StringUtils.isNotBlank(type), "status", type)
                        .like(StringUtils.isNotBlank(phone), "phone", phone)
                        .orderByDesc("create_time"));
        List<Orders> records = ordersPage.getRecords();
        //查询子订单项
        records.stream().forEach(item -> {
            User userByid = userservice.getById(item.getUserId());
            if (userByid != null) {
                item.setNickName(userByid.getNickname());
            }
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
        if (ordersById.getStatus().equals("完成")) {
            return Result.success();
        }
        ordersById.setStatus("完成");
        boolean update = ordersService.updateById(ordersById);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation(value = "用户获取自己的个人订单信息列表")
    @GetMapping("getOwnOrders")
    public Result getOwnOrders(@RequestHeader("userId") Long userId) {
        List<Orders> newOrderList = ordersService.list(new QueryWrapper<Orders>()
                .eq("user_id", userId)
                .eq("status", "已支付").or().eq("status", "未支付").orderBy(true, false, "create_time"));
        List<Orders> oldOrderList = ordersService.list(new QueryWrapper<Orders>()
                .eq("user_id", userId)
                .eq("status", "完成"));
        newOrderList.forEach(item -> {
            List<OrderItem> itemList = orderItemService.list(new QueryWrapper<OrderItem>().eq("order_id", item.getId()));
            item.setOrderItemList(itemList);
        });
        oldOrderList.forEach(item -> {
            List<OrderItem> itemList = orderItemService.list(new QueryWrapper<OrderItem>().eq("order_id", item.getId()));
            item.setOrderItemList(itemList);
        });
        HashMap<String, List<Orders>> map = new HashMap<>();
        map.put("newOrder", newOrderList);
        map.put("oldOrder", oldOrderList);
        return Result.success(map);
    }

    @ApiOperation(value = "获取订单详情")
    @GetMapping("getOwnOrder")
    public Result getOrderInfo(@RequestParam("orderId") Long orderId) {
        Orders orders = ordersService.getOne(new QueryWrapper<Orders>().eq("id", orderId));

        List<OrderItem> itemList = orderItemService.list(new QueryWrapper<OrderItem>().eq("order_id", orders.getId()));

        orders.setOrderItemList(itemList);

        return Result.success(orders);
    }

    @ApiOperation(value = "用户提交订单")
    @PostMapping("addUserOrder")
    public Result addUserOrder(@RequestBody OrderDTO orderDTO, @RequestHeader("userId") String userId) {
        return ordersService.addUserOrder(orderDTO, userId);
    }

    @ApiOperation(value = "用户支付订单")
    @PostMapping("payUserOrder")
    @Transactional
    public Result payUserOrder(@RequestBody Pay pay, @RequestHeader("userId") String userId) {
        User user = userservice.getOne(new QueryWrapper<User>().eq("id", userId));
        if ("余额".equals(pay.getOrderDTO().getPayType())) {
            if (pay.getOrderDTO().getTotal() > user.getIntegral()) {   //是否使用积分
                return Result.error("金额不足，请先充值");
            }
        }
        if (pay.getOrderDTO().getDeskId().equals("1") && pay.getOrderDTO().getSendType().equals("")) {
            return Result.error("请选择桌号或送餐方式");
        }
        if (pay.getOrderDTO().getTotal() > 120 || pay.getOrderDTO().getSendType().equals("送餐到桌")) {
            if (pay.getOrderDTO().getDeskId().equals("1")) {
                return Result.error("未选座");
            }
            deskController.updateDesksStatus(pay.getOrderDTO().getDeskId());
        }

        if (!pay.getOrderDTO().getDeskId().equals("1") && pay.getOrderDTO().getTotal() < 120 && pay.getOrderDTO().getSendType().equals("")) {
            return Result.error("金额不足，请选择到吧台就座");
        }


        user.setIntegral(user.getIntegral() - pay.getOrderDTO().getTotal());
        userservice.updateById(user);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("id", pay.getOrderDTO().getId());
        Orders orders = ordersService.getOne(wrapper);
        orders.setTotal(pay.getOrderDTO().getTotal());
        if (pay.getOrderDTO().getTotal() < 120) {
            orders.setDeskId("未选座");
        } else {
            Desk desk = deskservice.getOne(new QueryWrapper<Desk>().eq("id", pay.getOrderDTO().getDeskId()));
            orders.setDeskId(desk.getDeskNo());
        }
        orders.setPayType(pay.getOrderDTO().getPayType());
        orders.setRemark(pay.getOrderDTO().getRemark());
        orders.setStatus("已支付");
        ordersService.updateById(orders);
        return Result.success(200, "支付成功");
    }


    @ApiOperation(value = "用户取消订单")
    @Transactional
    @PutMapping("cancelOrder/{id}")
    public Result cancelOrder(@PathVariable int id) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        Orders order = ordersService.getOne(wrapper);
        order.setStatus("已取消");
        boolean update = ordersService.updateById(order);
        if (update) {
            return Result.success("取消订单成功");
        } else {
            return Result.error("取消订单失败");
        }
    }

    @PostMapping("test")
    public Result test(int pageNum, int pageSize, @RequestBody Alcohols alcohols) {
        Page<Alcohols> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Alcohols> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(alcohols.getName()), "name", alcohols.getName());
        Page<Alcohols> page1 = alcoholsService.page(page, wrapper);
        return Result.success(page1);
    }


    @ApiOperation(value = "管理员确认接单")
    @GetMapping("setOrderStatus")
    public Result setOrderStatus(@RequestParam("id") String id) {
        Orders ordersById = ordersService.getById(id);
        if (ordersById == null) {
            return Result.error("id有误");
        }
        if (ordersById.getStatus().equals("已接单")) {
            return Result.success();
        }
        ordersById.setStatus("已接单");
        boolean update = ordersService.updateById(ordersById);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }
}

