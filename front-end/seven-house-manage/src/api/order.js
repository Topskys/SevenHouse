/*
 * @Author: Topskys
 * @Date: 2022-10-19 20:50:54
 * @LastEditTime: 2022-11-20 15:43:42
 * 获取订单管理数据模块
 */
import request from '@/utils/request'

//获取订单列表或查询的接口
export const reqOrderList = (pageNum, limit, phone = '', status = '') => request({ url: `/api/orders/getUserOrders?pageNum=${pageNum}&pageSize=${limit}&phone=${phone}&type=${status}` })


// 确认订单
export const reqConfirmOrder = (data) => request({ url: `/api/orders/updateUserOrders?orderId=${data}` })


// 查询酒馆营业信息
export const reqIsOpenStore = () => request({ url: `/api/store/getStroeInfo` })

// 请求 休息中
export const reqCloseStore = () => request({ url: `/api/store/openOrCloseStroe` })


// 请求寄存酒
export function reqDeposit(data) {
    return request({
        url: `/api/deposit/addDeposit`,
        method: 'post',
        data
    })
}


//添加新或修改订单接口
// export const reqAddOrUpdateOrder = (order) => {
//     if (order.id) {
//         return request({
//             url: `/admin/order/update/`,
//             method: 'PUT',
//             data: order,
//         })
//     } else {
//         return request({ url: `/admin/order/save/`, method: 'post', data: order })
//     }
// }



// 删除订单接口
// export const reqDeleteOrder = (id) => request({
//     url: `/admin/order/remove/${id}`,
//     method: 'delete'
// })