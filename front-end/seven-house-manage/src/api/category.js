/*
 * @Author: Topskys
 * @Date: 2022-11-04 15:08:56
 * @LastEditTime: 2022-11-07 13:24:57
 */
import request from '@/utils/request'


export const reqCategoryList = () => request({ url: `/api/category/getcateGoryList` })


export const reqAddOrUpdateCategory = (data) => request({ url: `/api/category/addOrUpdate`, method: "post", data })


export const reqDelCategory = (data) => request({ url: `/api/category/delcateGoryList?id=${data}`, method: "get" })


export const reqCategoryQuery = (data) => request({ url: `/api/category/likeCateGory?likeStr=${data}` })