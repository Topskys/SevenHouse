import request from '@/utils/request'

// 获取sku列表接口
export const reqSkuList = (page,limit) => request({ url: `/admin/product/list/${page}/${limit}`, method: 'GET' })

// 上架sku接口
export const reqPushSale = (skuId) => request({ url: `/admin/product/onSale/${skuId}`, method: 'GET' })

// 下架sku接口
export const reqPullSale = (skuId) => request({ url: `/admin/product/cancelSale/${skuId}`, method: 'GET' })

// 获取sku详情接口
export const reqSkuById= (skuId) => request({ url: `/admin/product/getSkuById/${skuId}`, method: 'GET' })

// 获取sku详情接口
export const reqSkuByIds = (skuId) => request({ url: `/admin/product/getSkuById/${skuId}`, method: 'GET' })
