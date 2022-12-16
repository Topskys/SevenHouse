package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.xxl.common.Result;
import com.house.xxl.model.Deposit;
import com.house.xxl.model.DepositRecord;
import com.house.xxl.model.OrderItem;
import com.house.xxl.model.User;
import com.house.xxl.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
@Api(value = "寄存接口", tags = "寄存相关的接口")
@RestController
@RequestMapping("/deposit")
public class DepositController {

    @Resource
    private Depositservice depositService;

    @Resource
    private AlcoholsService alcoholsService;

    @Resource
    private Userservice userService;
    @Resource
    private DepositRecordService depositRecordService;

    @Resource
    private OrderItemservice orderItemservice;


    @ApiOperation(value = "管理员获取用户寄存的酒", notes = "type 0 进行中 1 已完成")
    @GetMapping("getDepositList")

    public Result getDepositList(@RequestParam("pageNum") Long pageNum,
                                 @RequestParam("pageSize") Long pageSize) {

        Page<Deposit> depositPage = depositService.page(new Page<Deposit>(pageNum, pageSize));
        List<Deposit> records = depositPage.getRecords();
        // 查询关联的酒信息
        records.stream().forEach(item -> {

            Integer userId = item.getUserId();
            //查询对应的用户
            User user = userService.getById(userId);
            item.setUser(user);
        });
        return Result.success(depositPage);
    }

    @ApiOperation(value = "用户获取自己寄存的酒")
    @GetMapping("getUserDepositList/{id}")
    public Result getUserDepositList(@PathVariable("id") String id, @RequestHeader("token") String token) {
        List<Deposit> depositList = depositService.list(new QueryWrapper<Deposit>()
                .eq("user_id", id));

        return Result.success(depositList);
    }


    @ApiOperation(value = "管理员添加寄存的酒")
    @PostMapping("addDeposit")
    public Result addDeposit(@RequestBody Deposit deposit) {
        String phone = deposit.getPhone();// 根据用户绑定的手机号添加寄存
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", phone));
        if (user == null) {
            return Result.error("手机号未绑定用户");
        }
        deposit.setCreateTime(new Date());
        deposit.setUpdateTime(new Date());
        String orderId = deposit.getOrderId();
        OrderItem orderItemserviceById = orderItemservice.getById(orderId);
        orderItemserviceById.setDeposit("1");
        orderItemservice.updateById(orderItemserviceById);
        boolean save = depositService.save(deposit);
        if (save) {
            return Result.success();
        }
        return Result.error();
    }


    @ApiOperation(value = "管理员模糊搜索用户寄存的酒")
    @GetMapping("searchDeposit")
    public Result addDeposit(@RequestParam("name") String name) {

        List<User> userList = userService.list(new QueryWrapper<User>().like("nickname", name));
        List<Deposit> depositLists = new ArrayList<>();
        userList.stream().forEach(items -> {
            List<Deposit> depositList = depositService.list(new QueryWrapper<Deposit>().eq("user_id", items.getId()));
            depositList.stream().forEach(item -> {
                Integer userId = item.getUserId();
                //查询对应的用户
                User user = userService.getById(userId);
                item.setUser(user);
                depositLists.add(item);
            });
        });

        return Result.success(depositLists);
    }

    @ApiOperation(value = "管理员删除用户寄存的酒")
    @GetMapping("delDeposit")
    public Result delDeposit(@RequestParam("id") String id) {
        boolean removeById = depositService.removeById(id);
        if (removeById) {
            return Result.success();
        }
        return Result.error();
    }


    @ApiOperation(value = "用户取出寄存的酒")
    @DeleteMapping("userDelDeposit")
    @Transactional
    public Result userDelDeposit(@RequestBody Deposit deposit, @RequestHeader("userId") Integer userId) {
        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
        wrapper.eq("id", deposit.getId());
        wrapper.eq("user_id", userId);
        Deposit one = depositService.getOne(wrapper);
        DepositRecord record = new DepositRecord();
        record.setNum(deposit.getDepNum());
        record.setDepId(Long.valueOf(deposit.getId()));
        record.setStatus("0");
        record.setUserId(userId.toString());
        record.setAlcoName(one.getAlcoName());
        record.setAlcoImg(one.getAlcoImage());
        record.setCreateTime(LocalDate.now());
        if (one.getDepNum() < deposit.getDepNum()) {
            return Result.error("取酒失败，取酒数量不对");
        } else if (one.getDepNum().equals(deposit.getDepNum())) {
            depositRecordService.save(record);
            depositService.removeById(one);
            return Result.success();
        } else {
            one.setDepNum(one.getDepNum() - deposit.getDepNum());
            depositRecordService.save(record);
            depositService.updateById(one);
            return Result.success();
        }
    }


//    @ApiOperation(value = "管理员审核用户寄存的酒")
//    @DeleteMapping("setStatus")
//    public Result setStatus(@RequestBody Deposit deposit, @RequestHeader("userId") Integer userId) {
//        QueryWrapper<Deposit> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", 2);
//        wrapper.eq("user_id", userId);
//        Deposit one = depositService.getOne(wrapper);
//        boolean removeById = depositService.updateById(one);
//        if (removeById) {
//            return Result.success();
//        }
//        return Result.error();
//    }
}

