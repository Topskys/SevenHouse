package com.house.xxl.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.house.xxl.common.Result;
import com.house.xxl.model.Orders;
import com.house.xxl.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
public interface Userservice extends IService<User> {

    Result checkLogin(String username, String password);

    Result userRegister(User user);
}
