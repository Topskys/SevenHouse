package com.house.xxl.web;


import com.house.xxl.common.Result;
import com.house.xxl.model.AlcoholsSku;
import com.house.xxl.service.AlcoholsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author auther: xxl
 * @since 2022-10-02
 */
@Api(value = "酒规格接口", tags = "酒规格相关的接口")
@RestController
@RequestMapping("/alcoholsSku")
public class AlcoholsSkuController {

    @Resource
    private AlcoholsSkuService alcoholsSkuService;

    @ApiOperation(value = "管理员新增酒规格", notes = "管理员新增酒规格")
    @PostMapping("addAlcoholsSku")
    public Result addAlcoholsSku(@RequestBody AlcoholsSku alcoholsSku) {
        long alcIdSku = System.currentTimeMillis() / 10 / 1000;//生成十位酒的id
        alcoholsSku.setId(alcIdSku);
        alcoholsSku.setCreateTime(LocalDateTime.now());
        boolean save = alcoholsSkuService.save(alcoholsSku);
        if (save) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation(value = "管理员删除酒规格", notes = "管理员删除酒规格")
    @GetMapping("delAlcoholsSku")
    public Result delAlcoholsSku(@RequestParam("id") String id) {
        boolean delete = alcoholsSkuService.removeById(id);
        if (delete) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation(value = "管理员修改酒规格", notes = "管理员修改酒规格")
    @GetMapping("updateAlcoholsSku")
    public Result updateAlcoholsSku(@RequestBody AlcoholsSku alcoholsSku) {
        boolean update = alcoholsSkuService.updateById(alcoholsSku);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }
}

