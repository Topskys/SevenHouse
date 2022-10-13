package com.house.xxl.web;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.xxl.common.Result;
import com.house.xxl.config.CustomException;
import com.house.xxl.model.User;
import com.house.xxl.service.Userservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private Userservice userservice;

    @Resource
    private HttpServletRequest request;

    public User getUser() {
        String token = request.getHeader("token");
        if (token == null) {
            throw new CustomException("500", "未登录");
        }
        System.out.println("#############################" + token);
        String userNo = JWT.decode(token).getAudience().get(0);
        System.out.println(userNo);
        return userservice.getOne(Wrappers.<User>lambdaQuery().eq(User::getId, userNo));
    }

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "登录用户名", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "登录密码", required = true)
    })
    @GetMapping("login")
    public Result login(String username, String password) {
        return userservice.checkLogin(username, password);
    }

    @ApiOperation("用户注册接口")
    @PostMapping("register")
    public Result register(@RequestBody User user) {
        return userservice.userRegister(user);
    }

    @ApiOperation("用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "userId", value = "用户id", required = true)
    })
    @GetMapping("userInfo")
    public Result userInfo(int userId) {
        User user = userservice.getById(userId);
        return Result.success(user);
    }


    @ApiOperation("管理员获取用户列表")
    @GetMapping("getUserList")
    public Result getUserList(@RequestParam("pageNum") Long pageNum, @RequestParam("pageSize") Long pageSize) {
        User user = getUser();
        if (!user.getStatus().equals("管理员")) {
            return Result.error("无权限");
        }
        Page<User> page = userservice.page(new Page<User>(pageNum, pageSize), new QueryWrapper<User>().eq("status", "用户"));
        return Result.success(page);
    }


    @ApiOperation("管理员修改用户信息")
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user) {
        User users = getUser();
        if (!users.getStatus().equals("管理员")) {
            return Result.error("无权限");
        }
        boolean updateById = userservice.updateById(user);
        if (updateById) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation("用户修改个人信息")
    @PostMapping("modifyUser")
    public Result modifyUser(@RequestBody User user) {
        User users = getUser();
        if (!users.getStatus().equals("用户")) {
            return Result.error("无权限");
        }
        boolean updateById = userservice.updateById(user);
        if (updateById) {
            return Result.success();
        }
        return Result.error();
    }



    @ApiOperation("管理员模糊查询用户信息")
    @GetMapping("likeUser")
    public Result likeUser(@RequestParam("name") String name) {
        List<User> list = userservice.list(new QueryWrapper<User>().like("username", name));
        return Result.success(list);
    }
}

