package com.house.xxl.web;


import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.house.xxl.common.Result;
import com.house.xxl.config.CustomException;
import com.house.xxl.model.User;
import com.house.xxl.service.Userservice;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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

//    public User getUser() {
//        Enumeration<String> headerNames = request.getHeaderNames();
//        String token = request.getHeader("token");
//        if (token == null) {
//            throw new CustomException(500, "未登录");
//        }
//        System.out.println("#############################" + token);
//        String userNo = JWT.decode(token).getAudience().get(0);
//        System.out.println(userNo);
//        return userservice.getOne(Wrappers.<User>lambdaQuery().eq(User::getId, userNo));
//    }

    @ApiOperation("用户登录接口")
    @ApiResponse(code = 200, message = "success")
    @PostMapping("login")
    public Result login(@RequestBody User user) {
        return userservice.checkLogin(user.getEmail(), user.getPassword());
    }

    @ApiOperation("用户注册接口")
    @PostMapping("register")
    public Result register(@RequestBody User user) {
        return userservice.userRegister(user);
    }

    @ApiOperation("用户信息接口")
    @GetMapping("userInfo/{id}")
    public Result userInfo(@PathVariable int id) {
        User user = userservice.getById(id);
        return Result.success(user);
    }


    @ApiOperation("管理员获取用户列表")
    @GetMapping("getUserList")
    public Result getUserList(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              @RequestParam(required = false) String userName,
                              @RequestParam(required = false) String phone) {
//        User user = getUser();
//        if (!user.getStatus().equals("管理员")) {
//            return Result.error("无权限");
//        }
        Page<User> page = userservice.page(new Page<User>(pageNum, pageSize),
                new QueryWrapper<User>().eq("type", "用户")
                        .like(StringUtils.isNotBlank(userName), "nickname", userName)
                        .like(StringUtils.isNotBlank(userName), "phone", phone));
        return Result.success(page);
    }


    @ApiOperation("管理员修改用户信息")
    @PostMapping("updateUser")
    public Result updateUser(@RequestBody User user) {
//        User users = getUser();
//        if (!users.getType().equals("管理员")) {
//            return Result.error("无权限");
//        }
        boolean updateById = userservice.updateById(user);
        if (updateById) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation("用户修改个人信息")
    @PostMapping("modifyUser")
    public Result modifyUser(@RequestBody User user) {
        if (!user.getType().equals("用户")) {
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

    @ApiOperation("用户删除信息")
    @GetMapping("deleteUser")
    public Result deleteUser(@RequestParam("userId") String userId) {
        boolean remove = userservice.removeById(userId);
        if (remove) {
            return Result.success();
        }
        return Result.error();
    }

    @ApiOperation("用户充值金额")
    @PutMapping("recharge")
    public Result recharge(@RequestBody User user,@RequestHeader("userId") Long userId,@RequestHeader("token") String token){
        if (StringUtils.isEmpty(token)){
            return Result.error("请先登录");
        }
        if (user.getIntegral()>=500){
            user.setIntegral(user.getIntegral()+100);
        }
        User one = userservice.getOne(new QueryWrapper<User>().eq("id", userId));
        one.setIntegral(user.getIntegral()+one.getIntegral());
        boolean b = userservice.updateById(one);
        if (b){
            return Result.success("充值成功");
        }else {
            return Result.success("充值失败");
        }
    }
}

