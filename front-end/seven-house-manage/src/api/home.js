/*
 * @Author: Topskys
 * @Date: 2022-10-19 20:50:54
 * @LastEditTime: 2022-11-17 23:43:51
 * 首页销售报表模块
 */
import request from '@/utils/request'

//获取首页图表数据的接口
export const reqMonthSale = () => request({ url: `/api/dataSource/getMonthTotal` })

export const reqVisitor = () => request({ url: `/api/dataSource/brose` })

export const reqHotSale = () => request({ url: `/api/dataSource/getHotAlc` })

export const reqPayNum = () => request({ url: `/api/dataSource/getOrderCount` })

export const reqSaleTotal = () => request({ url: `/api/dataSource/getOrderTotal` })


export const reqSaleDay = () => request({ url: `/api/dataSource/getTotalTotal` })

export const reqWeekDayRate = () => request({ url: `/api/dataSource/weekAndToday` })





