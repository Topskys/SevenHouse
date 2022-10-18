// 引入封装的请求工具包
import request from '@/utils/request'


// 请求获取订单接口，参数：token
export const reqOrderList = () => request({
	url: '/api/order/list',
})


// 请求更新订单接口，参数：order，token
export const reqUpdateOrder = (data) => request({
	url: '/api/order/update',
	method: 'post',
	data,
})



// 请求支付订单接口，参数：order，token
export const reqPayOrder = (data) => request({
	url: '/api/order/pay',
	method: 'post',
	data,
})


// 请求删除or取消订单接口，参数：order，token
export const reqDelOrder = (data) => request({
	url: '/api/order/del',
	method: 'post',
	data,
})
