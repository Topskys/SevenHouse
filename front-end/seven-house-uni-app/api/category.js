// 引入封装的请求工具包
import request from '@/utils/request'


// 请求分类列表
export const reqCategoryList = () => request({
	url: '/api/category/list'
})



// 请求分类项对应商品
export const reqProductList = (data) => request({
	url: `/api/category/product`,
	method: 'post',
	data,
})
