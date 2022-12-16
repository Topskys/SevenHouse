package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.xxl.common.Result;
import com.house.xxl.model.DepositRecord;
import com.house.xxl.model.User;
import com.house.xxl.service.AlcoholsService;
import com.house.xxl.service.DepositRecordService;
import com.house.xxl.service.Depositservice;
import com.house.xxl.service.Userservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
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
 * @since 2022-11-08
 */
@Api(value = "取酒记录接口", tags = "取酒记录相关的接口")
@RestController
@RequestMapping("/depositRecord")
public class DepositRecordController {

    @Resource
    private DepositRecordService depositRecordService;

    @Resource
    private Depositservice depositService;

    @Resource
    private Userservice userservice;

    @Resource
    private AlcoholsService alcoholsService;

    @ApiOperation(value = "获取取酒记录", notes = "获取取酒记录")
    @GetMapping("getDepositRecord")
    @Transactional(rollbackFor = Exception.class)
    public Result getDepositRecord(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                   @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                   @RequestParam(required = false) String alcoName
    ) {
        Page<DepositRecord> page = depositRecordService.page(new Page<DepositRecord>(pageNum, pageSize),
                new QueryWrapper<DepositRecord>().like(StringUtils.isNotBlank(alcoName), "alco_name", alcoName));
        List<DepositRecord> records = page.getRecords();
        for (DepositRecord record : records) {
            String userId = record.getUserId();
            User user = userservice.getById(userId);
            record.setUser(user);
        }
        return Result.success(page);
    }


    @ApiOperation(value = "删除取酒记录", notes = "删除取酒记录")
    @GetMapping("delDepositRecord")
    @Transactional(rollbackFor = Exception.class)
    public Result delDepositRecord(@RequestParam("id") String id) {
        boolean remove = depositRecordService.removeById(id);
        if (remove) {
            return Result.success();
        }
        return Result.error();
    }


    @ApiOperation(value = "管理员确认已经取出", notes = "管理员确认已经取出")
    @GetMapping("setDepositRecord")
    @Transactional(rollbackFor = Exception.class)
    public Result setDepositRecord(@RequestParam("id") String id) {
        DepositRecord byId = depositRecordService.getById(id);
        byId.setStatus("1");
        boolean updateById = depositRecordService.updateById(byId);
        if (updateById) {
            return Result.success();
        }
        return Result.error();
    }
}

