package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.xxl.common.Result;
import com.house.xxl.dto.custom;
import com.house.xxl.model.Desk;
import com.house.xxl.service.Deskservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "桌号接口", tags = "桌号相关的接口")
@RestController
@RequestMapping("/desk")
public class DeskController {

    @Resource
    private Deskservice deskService;

    @ApiOperation(value = "获取桌子信息")
    @GetMapping("getDeskList")
    public Result getStroeInfo() {
        List<Desk> desklist = deskService.list(new QueryWrapper<Desk>().ne("desk_status", "2"));
        custom custom = new custom();
        custom.setList(desklist);
        return Result.success(custom);
    }

    @ApiOperation(value = "获取所有桌子信息+模糊查询")
    @GetMapping("getLikeDeskList")
    public Result getStroeInfo(@RequestParam(name = "deskNo", required = false) String deskNo) {
        List<Desk> desklist = deskService.list(new QueryWrapper<Desk>()
                .like(StringUtils.isNotBlank(deskNo), "desk_no", deskNo)
        );

        return Result.success(desklist);
    }

    @ApiOperation(value = "获取空闲桌子信息")
    @GetMapping("getFreeDesk")
    public Result getFreeDesk() {
        List<Desk> list = deskService.list(new QueryWrapper<Desk>().eq("deskStatus", "0"));
        return Result.success(list);
    }

    @ApiOperation(value = "修改桌子状态")
    @PostMapping("updateDesksStatus")
    public Result updateDesksStatus(String id) {
        Desk deskById = deskService.getById(id);
        deskById.setDeskStatus("1");
        boolean update = deskService.updateById(deskById);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation(value = "修改桌子信息,0正常，1有人，2损坏")
    @PostMapping("updateDesk")
    public Result updateDesk(@RequestBody Desk desk) {

        boolean update = deskService.updateById(desk);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation(value = "新增桌子信息")
    @PostMapping("addDesk")
    public Result addDesk(@RequestBody Desk desk) {
        boolean save = deskService.save(desk);
        if (save) {
            return Result.success();
        }
        return Result.error();
    }


//    @ApiOperation(value = "设置损坏桌子")
//    @PostMapping("updateDesksBad")
//    public Result updateDesksBad(@RequestBody Desk desk) {
//        Desk deskById = deskService.getById(desk.getId());
//        deskById.setDeskStatus("2");
//        boolean update = deskService.updateById(deskById);
//        if (update) {
//            return Result.success();
//        }
//        return Result.error();
//    }

    @ApiOperation("删除desk信息")
    @GetMapping("deleteDesk")
    public Result deleteDesk(@RequestParam("id") String id) {
        boolean remove = deskService.removeById(id);
        if (remove) {
            return Result.success();
        }
        return Result.error();
    }

}

