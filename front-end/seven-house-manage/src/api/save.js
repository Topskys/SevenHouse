/*
 * @Author: Topskys
 * @Date: 2022-11-14 17:04:23
 * @LastEditTime: 2022-11-15 16:54:21
 * 寄存模块
 */
import request from '@/utils/request'


export const reqSaveList = (pageNum = 1, pageSize = 10) => request({ url: `/api/deposit/getDepositList?pageSize=${pageSize}&pageNum=${pageNum}` })


export const reqSaveQuery = (data) => request({ url: `/api/deposit/searchDeposit?name=${data}` })


export const reqDelDeposit = (id) => request({ url: `/api/deposit/delDeposit?id=${id}` })


// 取酒记录列表
export const reqRecordList = (alcoName, pageNum, pageSize) => request({ url: `/api/depositRecord/getDepositRecord?alcoName=${alcoName}&pageNum=${pageNum}&pageSize=${pageSize}` })


// 管理员确认存酒已取出
export const reqConfirmTakeOut = id => request({ url: `/api/depositRecord/setDepositRecord?id=${id}` })


//  删除取酒记录
export const reqDelRecord = id => request({ url: `/api/depositRecord/delDepositRecord?id=${id}` })