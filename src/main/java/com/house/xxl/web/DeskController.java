package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.house.xxl.common.Result;
import com.house.xxl.model.Desk;
import com.house.xxl.service.Deskservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Api(value = "桌号接口", tags = "桌号相关的接口")
@RestController
@RequestMapping("/desk")
public class DeskController {


    @Resource
    private Deskservice deskService;


    @ApiOperation(value = "获取桌子信息")
    @GetMapping("getDeskList")
    public Result getStroeInfo() {
        List<Desk> list = deskService.list();
        return Result.success(list);
    }

    @ApiOperation(value = "获取空闲桌子信息")
    @GetMapping("getFreeDesk")
    public Result getFreeDesk() {
        List<Desk> list = deskService.list(new QueryWrapper<Desk>().eq("deskStatus", "0"));
        return Result.success(list);
    }

    @ApiOperation(value = "修改桌子状态")
    @GetMapping("updateDesksStatus")
    public Result updateDesksStatus(@RequestParam("id") String id,
                                    @RequestParam("status") String status) {
        Desk deskById = deskService.getById(id);
        deskById.setDeskStatus(status);
        boolean update = deskService.updateById(deskById);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }


}

