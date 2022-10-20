/*
 * @Author: Topskys
 * @Date: 2022-10-07 10:27:10
 * @LastEditTime: 2022-10-07 11:43:21
 */

const data = require('./data.json')

module.exports = [
    {
        url: '/home/list',
        type: 'get',
        response: config => {
            return {
                code: 20000,
                data
            }
        }
    }
]
