/*
 * @Author: Topskys
 * @Date: 2022-05-22 00:29:52
 * @LastEditTime: 2022-10-05 15:25:11
 */
// SPU管理模块（类似于类）
import request from '@/utils/request'



// 获取SPU类数据列表数据接口
export const reqSPUList = (page, limit, category3Id) => request({
    url: `/admin/product/${page}/${limit}`,
    method: 'GET',
    params: {
        category3Id
    }
})


// 获取SPU信息接口
export const reqSPU = (spuId) => request({ url: `/admin/product/getSpuById/${spuId}`, method: 'GET' })


// 获取SPU品牌接口
export const reqTradeMarkList = () => request({ url: `/admin/product/baseTrademark/getTrademarkList`, method: 'GET' })


// 获取SPU图片接口
export const reqSPUImageList = (spuId) => request({ url: `/admin/product/spuImageLIst/${spuId}`, method: 'GET' })


// 获取SPU销售属性值3个
export const reqBaseSaleAttrList = () => request({ url: `/admin/product/baseSaleAttrList/`, method: 'GET' })



// 新增||修改SPU（修改带id）
export const reqAddOrUpdateSpu = (spuInfo) => {
    if (spuInfo.id) {
        return request({ url: `/admin/product/updateSpuInfo/`, method: 'post', data: spuInfo })
    } else {
        return request({ url: `/admin/product/saveSpuInfo/`, method: 'post', data: spuInfo })
    }
}




// 删除SPU
export const reqDeleteSpu = (spuId) => request({ url: `/admin/product/deleteSpu/${spuId}`, method: 'delete' })





/**
 * @description: 获取spu图片
 * @param {*} spuId
 * @return {*}
 */
export const reqSpuImageList = (spuId) => request({ url: `/admin/product/spuImageList/${spuId}`, method: 'GET' })




/**
 * @description: 获取spu销售属性
 * @param {*} spuId
 * @return {*}
 */
export const reqSpuSaleAttrList = (spuId) => request({ url: `/admin/product/spuSaleAttrList/${spuId}`, method: 'GET' })


/**
 * @description: 获取平台属性数据
 * @param {*} category1Id
 * @param {*} category2Id
 * @param {*} category3Id
 * @return {*}
 */
export const reqAttrInfoList = (category1Id, category2Id, category3Id) => request({ url: `/admin/product/attrInfoList/${category1Id}/${category2Id}/${category3Id}`, method: 'GET' })



// 添加SKU
export const reqAddSku = (skuInfo) => request({ url: `/admin/product/saveSkuInfo`, method: 'post', data: skuInfo })




// 获取SKU列表数据
export const reqSkuList = (spuId) => request({ url: `/admin/product/findBySpuId/${spuId}`, method: 'get' })
