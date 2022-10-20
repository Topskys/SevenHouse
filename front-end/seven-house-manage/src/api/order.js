/*
 * @Author: Topskys
 * @Date: 2022-10-19 20:50:54
 * @LastEditTime: 2022-10-19 20:58:13
 * 获取订单管理数据模块
 */
import request from '@/utils/request'

//获取订单列表的接口
export const reqOrderList = (page, limit) => request({ url: `/admin/order/list/${page}/${limit}`, method: 'get' })


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