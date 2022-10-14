// 引入封装的请求工具包
import request from '@/utils/request'

// 请求主页初始化数据：banners、seat、news
const reqHomeData = () => request({
	url: '/api/home/list',
	methods: 'GET'
})


// 顾客选座请求，参数：id
const reqSetSeat = (id) => request({
	url: `/api/home/list/${id}`,
	methods: 'GET'
})
