package com.house.xxl.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String md5(String password){
        //生成一个MD5加密
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //计算MD5的值
            md5.update(password.getBytes());
            //BigInteger 将8位的字符串转成16位的字符串，得到的字符串形式是哈希码值
            //BigInteger(参数1,参数2) 参数1 是1为正数，0为0，-1为负数
            return new BigInteger(1,md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
