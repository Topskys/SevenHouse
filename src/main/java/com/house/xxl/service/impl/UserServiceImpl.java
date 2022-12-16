package com.house.xxl.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.house.xxl.common.MD5Util;
import com.house.xxl.common.Result;
import com.house.xxl.model.User;
import com.house.xxl.dao.UserMapper;
import com.house.xxl.service.Userservice;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auther: xxl
 * @since 2022-09-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements Userservice {

    @Resource
    private UserMapper userMapper;

    @Override
    public Result checkLogin(String username, String password) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getEmail,username);
        User user = userMapper.selectOne(wrapper);
        if (user==null){
            return new Result("用户不存在");
        }else {
            String md5 = MD5Util.md5(password);
            if (md5.equals(user.getPassword())){
                JwtBuilder builder = Jwts.builder();

                String token = builder.setSubject(username)    //主题，就是token中携带二等数据
                        .setIssuedAt(new Date())               //设置token的生成日期
                        .setId(user.getId() + "")          //设置用户id为token
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))  //设置token过期时间
                        .signWith(SignatureAlgorithm.HS256, "zzh123456")             //设置加密方式和加密密码
                        .compact();
                user.setToken(token);
                user.setToken(token);
                return Result.success(user);
            }else {
                return Result.error("账号密码错误");
            }
        }
    }

    @Override
    @Transactional
    public Result userRegister(User user) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getEmail, user.getEmail());
        User user1 = userMapper.selectOne(wrapper);
        if (user1==null){
            String md5Pwd = MD5Util.md5(user.getPassword());
            user.setPassword(md5Pwd);
            user.setIntegral(0l);
            user.setStatus("0");
            user.setBirthday("0");
            user.setType("用户");
            user.setGender("0");
            user.setNickname(new Random().nextInt(100000) +"");
            user.setPhone("未知");
            user.setAddress("未知");
            user.setCreateTime(LocalDate.now());
            user.setUpdateTime(LocalDateTime.now());
            int i = userMapper.insert(user);
            if (i>0){
                return Result.success("注册成功");
            }else {
                return Result.success("注册失败");
            }
        }else {
            return Result.error("用户已经被注册");
        }
    }

}
