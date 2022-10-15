// 引入封装的请求工具包
import request from '@/utils/request'


// 请求登录接口，参数：email、password
export const reqLogin = (data) => request({
	url: '/api/user/login',
	methods: 'post',
	data,
})


// 请求注册信息，参数：email、password、phone
export const reqRegister = (data) => request({
	url: '/api/user/register',
	methods: 'post',
	data,
})


// 请求修改用户信息
export const reqUpdateInfo = (data) => request({
	url: '/api/user/updateInfo',
	methods: 'post',
	data,
})


// 请求查询用户存酒列表信息
export const reqSaveWineList = (data) => request({
	url: '/api/user/saveWineList',
	methods: 'get',
	data
})


// 请求取酒接口
export const reqTakeoutWine = (data) => request({
	url: '/api/user/saveWineList',
	methods: 'post',
	data
})



// 领取优惠劵
export const reqTakeDiscount = (data) => request({
	url: '/api/user/saveWineList',
	methods: 'get',
	data
})
