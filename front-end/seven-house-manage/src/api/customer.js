/*
 * @Author: Topskys
 * @Date: 2022-10-19 20:50:54
 * @LastEditTime: 2022-11-08 16:06:05
 * 顾客管理模块
 */
import request from '@/utils/request'

// 获取顾客列表或查询的接口
export const reqCustomerList = (pageNum = 1, pageSize = 10, phone = '', userName = '') => request({ url: `/api/user/getUserList?pageSize=${pageSize}&pageNum=${pageNum}&phone=${phone}&userName=${userName}` })


// 修改顾客接口
export const reqCustomerUpdate = (data) => request({ url: '/api/user/updateUser', method: 'post', data })


// 删除接口
export const reqDeleteCustomer = (id) => request({ url: `/api/user/deleteUser?userId=${id}` })




//添加新或修改顾客接口
// export const reqAddOrUpdateOrder = (customer) => {
//     customer.id ? request({
//         url: `/admin/customer/update/`,
//         method: 'PUT',
//         data: customer,
//     }) : request({ url: `/admin/customer/save/`, method: 'post', data: customer })
// }