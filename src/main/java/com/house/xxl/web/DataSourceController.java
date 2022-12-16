package com.house.xxl.web;

import com.house.xxl.common.Result;
import com.house.xxl.dao.OrdersMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author chr
 * @Description
 * @create 2022-11-09 21:36
 */
@Api(value = "数据分析接口", tags = "数据分析接口")
@RestController
@RequestMapping("/dataSource")
public class DataSourceController {


    @Resource
    private OrdersMapper ordersMapper;

    @ApiOperation(value = "获取销售总额", notes = "获取销售总额")
    @GetMapping("getOrderTotal")
    public Result getOrderTotal() {
        Integer orderTotal = ordersMapper.getOrderTotal();
        return Result.success(orderTotal);
    }

    @ApiOperation(value = "获取当天销售总额", notes = "获取当天销售总额")
    @GetMapping("getTotalTotal")
    public Result getTotalTotal() {
        Integer orderTotal = ordersMapper.getTodayTotal();
        return Result.success(orderTotal);
    }

    @ApiOperation(value = "查询支付笔数", notes = "查询支付笔数")
    @GetMapping("getOrderCount")
    public Result getOrderCount() {
        Integer orderTotal = ordersMapper.getOrderCount();

        return Result.success(orderTotal);
    }

    @ApiOperation(value = "获取十二个月的销售额", notes = "获取十二个月的销售额,年份默认2022")
    @GetMapping("getMonthTotal")
    public Result getMonthTotal(@RequestParam(value = "year", defaultValue = "2022") String year) {
        List<String> date = getDate(year);
        List<Map<String, String>> list = new LinkedList();
        for (String s : date) {
            Map<String, String> map = new LinkedHashMap<>();
            Integer monthTotal = ordersMapper.getMonthTotal(year, s);
            if (monthTotal == null) {
                map.put("time", s);
                map.put("count", "0");
                list.add(map);
                continue;
            }
            map.put("time", s);
            map.put("count", monthTotal.toString());
            list.add(map);
        }

        return Result.success(list);
    }

    List<String> getDate(String year) {
        List<String> date = new LinkedList<>();
        for (int i = 1; i < 13; i++) {
            if (i < 10) {
                date.add(year + "-0" + i);
                continue;
            }
            date.add(year + "-" + i);
        }
        return date;
    }

    @ApiOperation(value = "获取热销榜单前十", notes = "获取热销榜单前十")
    @GetMapping("getHotAlc")
    public Result getHotAlc() {
        List<Map<String, String>> hot = ordersMapper.getHot();

        return Result.success(hot);
    }


    @ApiOperation(value = "周同比和日同比", notes = "周同比和日同比")
    @GetMapping("weekAndToday")
    public Result weekAndToday() {
        Integer week = (int) (1 + Math.random() * 10);
        Integer day = (int) (1 + Math.random() * 10);
        Map<String, String> map = new HashMap<>();
        map.put("week", week.toString());
        map.put("day", day.toString());
        return Result.success(map);
    }


    @ApiOperation(value = "访问量", notes = "周同比和日同比")
    @GetMapping("brose")
    public Result brose() {
        Integer brose = (int) (1 + Math.random() * 1000);
        Map<String, String> map = new HashMap<>();
        map.put("brose", brose.toString());
        return Result.success(map);
    }
}
