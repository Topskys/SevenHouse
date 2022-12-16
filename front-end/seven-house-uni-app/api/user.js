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
	url: '/api/user/modifyUser',
	method: 'post',
	data,
})



// 请求用户信息
export const reqUserInfo = (id) => request({
	url: `/api/user/userInfo/${id}`,
})


// 请求查询用户存酒列表信息
export const reqSaveWineList = (id) => request({
	url: `/api/deposit/getUserDepositList/${id}`,
})


// 请求取酒接口
export const reqTakeoutWine = (data) => request({
	url: '/api/deposit/userDelDeposit',
	method: 'delete',
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


// 充值接口
export const reqPayIn = (data) => request({
	url: '/api/user/recharge',
	method: 'put',
	data
})
