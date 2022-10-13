package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.xxl.common.Result;
import com.house.xxl.model.Alcohols;
import com.house.xxl.model.Deposit;
import com.house.xxl.model.User;
import com.house.xxl.service.AlcoholsService;
import com.house.xxl.service.Depositservice;
import com.house.xxl.service.Userservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Controller
@RequestMapping("/deposit")
public class DepositController {

    @Resource
    private Depositservice depositService;

    @Resource
    private AlcoholsService alcoholsService;

    @Resource
    private Userservice userService;

    @ApiOperation(value = "管理员获取用户寄存的酒", notes = "type 0 进行中 1 已完成")
    @GetMapping("getDepositList")
    public Result getDepositList(@RequestParam("pageNum") Long pageNum,
                                 @RequestParam("pageSize") Long pageSize) {

        Page<Deposit> depositPage = depositService.page(new Page<Deposit>(pageNum, pageSize),
                new QueryWrapper<Deposit>().ne("user_id", "-1"));
        List<Deposit> records = depositPage.getRecords();
        // 查询关联的酒信息
        records.stream().forEach(item -> {
            Integer barId = item.getBarId();
            Integer userId = item.getUserId();
            Alcohols alcoholsById = alcoholsService.getById(barId);
            //查询对应的用户
            User user = userService.getById(userId);
            item.setUser(user);
            item.setAlcohols(alcoholsById);
        });
        return Result.success(depositPage);
    }

    @ApiOperation(value = "用户获取自己寄存的酒")
    @GetMapping("getUserDepositList")
    public Result getUserDepositList(@RequestParam("id") String id) {
        List<Deposit> depositList = depositService.list(new QueryWrapper<Deposit>().ne("user_id", id));
        // 查询关联的酒信息
        depositList.stream().forEach(item -> {
            Integer barId = item.getBarId();
            Alcohols alcoholsById = alcoholsService.getById(barId);
            item.setAlcohols(alcoholsById);
        });
        return Result.success(depositList);
    }


    @ApiOperation(value = "管理员为用户添加寄存的酒")
    @PostMapping("addDeposit")
    public Result addDeposit(@RequestBody Deposit deposit) {
        String phone = deposit.getPhone();// 根据用户绑定的手机号添加寄存
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", phone));
        if (user == null) {
            return Result.error("手机号未绑定用户");
        }
        deposit.setUserId(Integer.parseInt(user.getId().toString()));
        deposit.setCreateTime(LocalDateTime.now());
        deposit.setUpdateTime(LocalDateTime.now());
        boolean save = depositService.save(deposit);
        if (save) {
            return Result.success();
        }
        return Result.error();
    }


    @ApiOperation(value = "管理员模糊搜索用户寄存的酒")
    @GetMapping("searchDeposit")
    public Result addDeposit(@RequestParam("name") String name) {

        List<User> userList = userService.list(new QueryWrapper<User>().like("username", name));
        List<Deposit> depositLists = new ArrayList<>();
        userList.stream().forEach(items -> {
            List<Deposit> depositList = depositService.list(new QueryWrapper<Deposit>().ne("user_id", items.getId()));
            depositList.stream().forEach(item -> {
                Integer barId = item.getBarId();
                Integer userId = item.getUserId();
                Alcohols alcoholsById = alcoholsService.getById(barId);
                //查询对应的用户
                User user = userService.getById(userId);
                item.setUser(user);
                item.setAlcohols(alcoholsById);
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
}

