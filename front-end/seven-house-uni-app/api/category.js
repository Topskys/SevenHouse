// 引入封装的请求工具包
import request from '@/utils/request'


// 请求分类列表
export const reqCategoryList = () => request({
	url: '/api/category/list',
	methods: 'GET'
})
