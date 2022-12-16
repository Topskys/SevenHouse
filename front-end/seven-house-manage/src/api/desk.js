/*
 * @Author: Topskys
 * @Date: 2022-11-04 23:39:29
 * @LastEditTime: 2022-11-12 15:00:13
 */
import request from '@/utils/request'


export const reqDeskList = (input) => request({ url: `/api/desk/getLikeDeskList?deskNo=${input}` })


export const reqAddOrUpdateDesk = (data) => data.id ? request({ url: `/api/desk/updateDesk`, method: "post", data }) : request({ url: `/api/desk/addDesk`, method: "post", data })


export const reqDelDesk = (data) => request({ url: `/api/desk/deleteDesk?id=${data}` })

