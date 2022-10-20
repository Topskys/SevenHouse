//获取品牌数据管理的模块
import request from '@/utils/request'

//获取品牌列表的接口
export const reqTradeMarkList = (page, limit) => request({ url: `/admin/product/baseTrademark/${page}/${limit}`, method: 'GET' })


//添加新品牌接口//修改品牌接口
export const reqAddOrUpdateTradeMark = (tradeMark) => {
    if (tradeMark.id) {
        return request({
            url: `/admin/product/baseTrademark/update/`,
            method: 'PUT',
            data: tradeMark,
        })
    } else {
        return request({ url: `/admin/product/baseTrademark/save/`, method: 'post', data: tradeMark })
    }
}



//删除品牌接口
export const reqDeleteTradeMark = (id) => request({
    url: `/admin/product/baseTrademark/remove/${id}`,
    method: 'delete'
})