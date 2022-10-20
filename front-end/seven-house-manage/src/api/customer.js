/*
 * @Author: Topskys
 * @Date: 2022-10-19 20:50:54
 * @LastEditTime: 2022-10-20 17:39:14
 * 顾客管理模块
 */
import request from '@/utils/request'

//获取顾客列表的接口
export const reqCustomerList = (pageNum, pageSize) => request({ url: `/user/getUserList/${pageNum}/${pageSize}` })


//添加新或修改订单接口
export const reqAddOrUpdateOrder = (order) => {
    if (order.id) {
        return request({
            url: `/admin/order/update/`,
            method: 'PUT',
            data: order,
        })
    } else {
        return request({ url: `/admin/order/save/`, method: 'post', data: order })
    }
}



// 删除订单接口
export const reqDeleteOrder = (id) => request({
    url: `/admin/order/remove/${id}`,
    method: 'delete'
})