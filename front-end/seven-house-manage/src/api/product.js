/*
 * @Author: Topskys
 * @Date: 2022-10-19 20:50:54
 * @LastEditTime: 2022-11-13 13:54:44
 * 商品管理获取数据模块
 */
import request from '@/utils/request'

//获取商品列表或查询的接口
export const reqProductList = (name = '', pageNum = 1, pageSize = 10, type = '') => request({ url: `/api/alcohols/getAllAlc?name=${name}&pageNum=${pageNum}&pageSize=${pageSize}&type=${type}` })


//添加或修改商品的接口
// export const reqAddOrUpdateProduct = (data) => request({ url: data.alcoId ? `/api/alcohols/updateAlcoholsAndSku` : `/api/alcohols/addAlcohols`, method: 'post', data })
export function reqAddOrUpdateProduct(data) {
    return request({
        url: data.alcoId ? `/api/alcohols/updateAlcoholsAndSku` : `/api/alcohols/addAlcohols`,
        method: 'post',
        data
    })
}


//添加新或修改商品规格的接口
export const reqAddOrUpdateProductSKU = (data) => request({ url: data.id ? `/api/alcoholsSku/updateAlcoholsSku` : `/api/alcoholsSku/addAlcoholsSku`, method: 'post', data, })


// 新增或更新商品规格
export const reqAddOrUpdateSKU = (data) => request({url:data.id?'/api/alcoholsSku/updateAlcoholsSku':`/api/alcoholsSku/addAlcoholsSku`, method: 'post', data })



export const reqDelProduct = (data) => request({ url: `/api/alcohols/delAlcohols?alcoId=${data}` })



export const reqDelProductSKU = (data) => request({ url: `/api/alcoholsSku/delAlcoholsSku?id=${data}` })


// 删除type传del,下架传sub
export const reqDelOrPullProduct = (alcoId, type) => request({ url: `/api/alcohols/delOrSubAlcoholsAndSku?id=${alcoId}&type=${type}` })











// 删除订单接口
export const reqDeleteProduct = (id) => request({
    url: `/admin/product/delete/${id}`,
    method: 'delete'
})


//获取商品接口
export const reqProductById = (id) => request({ url: `/admin/product/${id}` })

// 上架商品接口
export const reqPushProduct = (id) => request({ url: `/admin/product/push/${id}` })

// 下架商品接口
export const reqPullProduct = (id) => request({ url: `/admin/product/pull/${id}` })
