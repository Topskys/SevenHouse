package com.house.xxl.config;


import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author chr
 * @Description
 * @create 2022-09-16 16:50
 */

public class GeneratorCode {

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();
//        String projectPath = System.getProperty("D:\\JavaIDEA\\web-admin");
        gc.setActiveRecord(true);// 是否支持ar模式
        gc.setOutputDir("F:\\work\\qf\\ServenHouse\\src\\main\\java");
        gc.setFileOverride(false);  //文件覆盖
        gc.setOpen(false);      //生成代码后是否打开文件夹
        gc.setIdType(IdType.AUTO); // 主键策略
        gc.setServiceName("%sService");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor("auther: xxl");
        mpg.setGlobalConfig(gc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3307/bar?useUnicode=true&characterEncoding=UTF-8&useSSL=true&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.house.xxl");
        pc.setController("web");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao");
        pc.setEntity("model");
        pc.setXml("dao");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
//        strategy.setInclude(new String[]{"user","orders","order_item","desk","deposit","business","alcohols_sku","alcohols"}); // 表名
        strategy.setInclude(new String[]{"alcohols_sku","alcohols"}); // 表名
//        strategy.setInclude(new String[]{"category"}); // 表名

        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
