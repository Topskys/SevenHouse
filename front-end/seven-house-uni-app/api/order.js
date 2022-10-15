// 引入封装的请求工具包
import request from '@/utils/request'


// 请求获取订单接口，参数：userInfo
export const reqOrderList = (data) => request({
	url: '/api/order/list',
	methods: 'post',
	data
})


// 请求更新订单接口，参数：order，userInfo
export const reqUpdateOrder = (data) => request({
	url: '/api/updateOrder',
	methods: 'post',
	data,
})



// 请求支付订单接口，参数：order，userInfo
export const reqPayOrder = (data) => request({
	url: '/api/payOrder',
	methods: 'post',
	data,
})
