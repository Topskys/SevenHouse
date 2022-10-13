package com.house.xxl.web;


import com.house.xxl.common.Result;
import com.house.xxl.model.Store;
import com.house.xxl.service.StoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author auther: xxl
 * @since 2022-10-02
 */
@Api(value = "店铺商家接口", tags = "店铺商家相关的接口")
@RestController
@RequestMapping("/store")
public class StoreController {

    @Resource
    private StoreService storeService;

    @ApiOperation(value = "获取店铺信息")
    @GetMapping("getStroeInfo")
    public Result getStroeInfo() {
        Store store = storeService.getById(1);
        return Result.success(store);
    }


    @ApiOperation(value = "管理员开店关店")
    @GetMapping("openOrCloseStroe")
    public Result openOrCloseStroe() {
        Store store = storeService.getById(1);
        if (store.getStatus().equals("0")) {
            store.setStatus("1");
        } else {
            store.setStatus("0");
        }
        boolean update = storeService.updateById(store);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }


    @ApiOperation(value = "管理员获取销售数据---暂时预留")
    @GetMapping("getStoreSalse")
    public Result getStoreSalse() {
        return Result.success();
    }


}

