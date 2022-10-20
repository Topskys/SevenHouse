package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.xxl.common.Result;
import com.house.xxl.model.Alcohols;
import com.house.xxl.model.AlcoholsSku;
import com.house.xxl.model.Category;
import com.house.xxl.service.AlcoholsService;
import com.house.xxl.service.AlcoholsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author auther: xxl
 * @since 2022-10-02
 */
@Api(value = "酒类接口", tags = "酒相关的接口")
@RestController
@RequestMapping("/alcohols")
public class AlcoholsController {

    @Resource
    private AlcoholsService alcoholsService;

    @Resource
    private AlcoholsSkuService alcoholsSkuService;

    @ApiOperation(value = "管理员新增酒和酒的规格", notes = "管理员新增酒和酒的规格")
    @PostMapping("addAlcoholsAndSku")
    @Transactional(rollbackFor = Exception.class)
    public Result addAlcoholsAndSku(@RequestBody Alcohols alcohols) {
        long alcId = System.currentTimeMillis() / 10 / 1000;//生成十位酒的id
        long alcIdSku = System.currentTimeMillis() / 10 / 1000;//生成十位酒的id
        alcohols.setAlcoId(alcId);
        alcohols.setCreateTime(LocalDateTime.now());
        alcohols.setUpdateTime(LocalDateTime.now());
        alcohols.setAlcoStatus("0");
        //酒对应多种规格
        List<AlcoholsSku> alcoholsSkus = alcohols.getAlcoholsSku();
        alcoholsSkus.stream().forEach(item -> {
            item.setAlcoId(alcId);
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            item.setId(alcIdSku);
            alcoholsSkuService.save(item);
        });
        boolean save = alcoholsService.save(alcohols);

        if (save) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation(value = "管理员修改酒和酒的规格", notes = "管理员修改酒和酒的规格")
    @PostMapping("updateAlcoholsAndSku")
    @Transactional(rollbackFor = Exception.class)
    public Result updateAlcoholsAndSku(@RequestBody Alcohols alcohols) {
        alcohols.setUpdateTime(LocalDateTime.now());
        alcohols.setAlcoStatus("0");
        List<AlcoholsSku> alcoholsSkus = alcohols.getAlcoholsSku();
        alcoholsSkus.stream().forEach(item -> {
            item.setUpdateTime(LocalDateTime.now());
            alcoholsSkuService.updateById(item);
        });
        boolean save = alcoholsService.updateById(alcohols);
        if (save) {
            return Result.success();
        }
        return Result.error();
    }


    @ApiOperation(value = "管理员删除或者下架酒和酒的规格", notes = "是删除type传del,下架传sub")
    @GetMapping("delOrSubAlcoholsAndSku")
    @Transactional(rollbackFor = Exception.class)
    public Result delAlcoholsAndSku(@RequestParam("id") String id, @RequestParam("type") String type) {
        if (!type.equals("del") || !type.equals("sub")) {
            return Result.error("参数有误");
        }
        Alcohols serviceById = alcoholsService.getById(id);
        if (type.equals("del")) {
            serviceById.setAlcoStatus("2");
        } else if (type.equals("sub")) {
            serviceById.setAlcoStatus("1");
        }
        boolean update = alcoholsService.updateById(serviceById);
        if (update) {
            return Result.success();
        }
        return Result.error();
    }


    @ApiOperation(value = "获取所有正在销售的酒", notes = "获取所有正在销售的酒")
    @GetMapping("getAllAlc")
    @Transactional(rollbackFor = Exception.class)
    public Result getAllAlc() {
        List<Alcohols> alcoholsList = alcoholsService.list(new QueryWrapper<Alcohols>().eq("alco_status", "0"));
        alcoholsList.stream().forEach(item -> {
            Long alcoId = item.getAlcoId();
            List<AlcoholsSku> list = alcoholsSkuService.list(new QueryWrapper<AlcoholsSku>().eq("alco_id", alcoId));
            if (list != null) {
                item.setAlcoholsSku(list);
            }
        });
        return Result.success(alcoholsList);
    }

    @ApiOperation(value = "模糊查询酒的信息", notes = "模糊查询酒的信息")
    @GetMapping("likeAlc")
    public Result likeAlc(@RequestParam("name") String name) {
        List<Alcohols> alcoholsList = alcoholsService.list(new QueryWrapper<Alcohols>().like("alco_name", name));
        alcoholsList.stream().forEach(item -> {
            Long alcoId = item.getAlcoId();
            List<AlcoholsSku> list = alcoholsSkuService.list(new QueryWrapper<AlcoholsSku>().eq("alco_id", alcoId));
            if (list != null) {
                item.setAlcoholsSku(list);
            }
        });

        return Result.success(alcoholsList);
    }

    @ApiOperation(value = "获取某一个类别正在销售的酒和酒的规格",notes = "获取某一个类别正在销售的酒和酒的规格")
    @GetMapping("getAllAlcAndSku")
    @Transactional
    public Result getAllAlcAndSku(@RequestParam("type") String type){
        List<Alcohols> alcoholsTypeList = alcoholsService.list(new QueryWrapper<Alcohols>().eq("alco_status", "0").eq("alco_type", type));
        if (alcoholsTypeList!=null){
            alcoholsTypeList.forEach(item->{
                List<AlcoholsSku> alcoholsSkus = alcoholsSkuService.list(new QueryWrapper<AlcoholsSku>().eq("alco_id", item.getAlcoId()));
                item.setAlcoholsSkuList(alcoholsSkus);
            });
        }

        return Result.success(alcoholsTypeList);
    }

}

