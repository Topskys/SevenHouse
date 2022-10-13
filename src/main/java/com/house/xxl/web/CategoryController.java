package com.house.xxl.web;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.xxl.common.Result;
import com.house.xxl.model.Category;
import com.house.xxl.service.CategoryService;
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
 * @since 2022-10-02
 */
@Api(value = "分类接口", tags = "分类相关的接口")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    @GetMapping("getcateGoryList")
    @ApiOperation(value = "管理员获取酒的分类", notes = "管理员获取酒的分类")
    public Result getcateGoryList() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    @GetMapping("delcateGoryList")
    @ApiOperation(value = "管理员删除酒的分类", notes = "管理员删除酒的分类")
    public Result getcateGoryList(@RequestParam("id") String id) {
        boolean removeById = categoryService.removeById(id);
        if (removeById) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @ApiOperation(value = "管理员模糊查询酒的分类", notes = "管理员模糊查询酒的分类")
    @GetMapping("likeCateGory")
    public Result likeCateGory(@RequestParam("likeStr") String likeStr) {
        List<Category> list = categoryService.list(new QueryWrapper<Category>().like("name", likeStr));
        return Result.success(list);
    }

    @ApiOperation(value = "管理员增加或者删除酒的分类", notes = "管理员增加或者删除酒的分类")
    @PostMapping("addOrUpdate")
    public Result addOrUpdate(@RequestBody Category category) {
        boolean saveOrUpdate = categoryService.saveOrUpdate(category);
        if (saveOrUpdate) {
            return Result.success();
        } else {
            return Result.error();
        }
    }


}

