// 引入封装的请求工具包
import request from '@/utils/request'


// 请求登录接口，参数：email、password
export const reqLogin = (data) => request({
	url: '/api/user/login',
	method: 'post',
	data
})


// 请求注册信息，参数：email、password、phone
export const reqRegister = (data) => request({
	url: '/api/user/register',
	method: 'post',
	data,
})


// 请求修改用户信息
export const reqUpdateInfo = (data) => request({
	url: '/api/userInfo/update',
	method: 'post',
	data,
})



// 请求修改用户信息
export const reqUserInfo = () => request({
	url: '/api/userInfo',
})


// 请求查询用户存酒列表信息
export const reqSaveWineList = () => request({
	url: '/api/saveWine/list',
})


// 请求取酒接口
export const reqTakeoutWine = (data) => request({
	url: '/api/saveWine/take',
	method: 'post',
	data
})

// 获取优惠劵列表
export const reqDiscountList = () => request({
	url: '/api/discount/list'
})



// 领取优惠劵
export const reqTakeDiscount = (data) => request({
	url: '/api/discount/take',
	method: 'post',
	data
})
