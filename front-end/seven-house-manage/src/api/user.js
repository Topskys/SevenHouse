/*
 * @Author: Topskys
 * @Date: 2022-10-19 17:38:12
 * @LastEditTime: 2022-11-07 12:53:29
 */
import request from '@/utils/request'

// export function login(data) {
//     return request({
//         url: '/admin/acl/index/login',
//         method: 'post',
//         data
//     })
// }

export function login(data) {
    return request({
        url: '/api/user/login',
        method: 'post',
        data
    })
}

export function getInfo(token) {
    return request({
        url: '/admin/acl/index/info',
        method: 'get',
        params: { token }
    })
}

// export function getInfo(token) {
//     return request({
//         url: '/admin/info',
//         method: 'get',
//         params: { token }
//     })
// }


export function logout() {
    return request({
        url: '/admin/acl/index/logout',
        method: 'post'
    })
}


// export function logout() {
//     return request({
//         url: '/admin/logout',
//         method: 'post'
//     })
// }