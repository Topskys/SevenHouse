// 平台属性管理模块
import request from '@/utils/request'


// 获取多级分类数据接口1\2\3
export const reqCategory1List = () => request({ url: '/admin/product/getCategory1', method: 'GET' })

export const reqCategory2List = (category1Id) => request({ url: `/admin/product/getCategory2/${category1Id}`, method: 'GET' })

export const reqCategory3List = (category2Id) => request({ url: `/admin/product/getCategory3/${category2Id}`, method: 'GET' })


//获取平台属性数据接口
export const reqAttrList = (category1Id, category2Id, category3Id) => request({ url: `/admin/product/attrInfoList/${category1Id}/${category2Id}/${category3Id}`, method: 'GET' })


// 添加or修改属性值接口
export const reqAddOrUpdateAttr = (data) => request({ url: `/admin/product/saveAttrInfo`, method: 'POST', data })