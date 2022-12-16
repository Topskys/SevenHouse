// 引入封装的请求工具包
import request from '@/utils/request'


// 请求获取订单接口
export const reqOrderList = () => request({
	url: '/api/orders/getOwnOrders',
})


// 请求添加订单接口
export const reqUpdateOrder = (data) => request({
	url: '/api/orders/addUserOrder',
	method: 'post',
	data,
})



// 请求支付订单接口，参数：order，token
export const reqPayOrder = (data) => request({
	url: `/api/orders/payUserOrder`,
	method: 'post',
	data
})


// 请求删除or取消订单接口，参数：order，token
export const reqDelOrder = (id) => request({
	url: `/api/orders/cancelOrder/${id}`,
	method: 'put',
})


// 请求取消订单接口
export const reqCancelOrder = (id) => request({
	url: `/api/orders/cancelOrder/${id}`,
	method: 'put',
})




// 检查点库是否开启接口
export const reqIsOpenStore = () => request({
	url: `/api/store/getStroeInfo`,
})



// 获取空闲桌子信息
export const getFreeDesk = () => request({
	url: `/api/desk/getFreeDesk`,
})

