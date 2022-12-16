// 引入封装的请求工具包
import request from '@/utils/request.js'

// 请求主页初始化数据：banners、seat、news
export const reqHomeData = () => request({
	url: '/api/desk/getDeskList'
})


// 顾客选座请求，参数：账户、座号
export const reqSetSeat = (data) => request({
	url: "/api/desk/updateDesksStatus",
	method: "post",
	data
})
