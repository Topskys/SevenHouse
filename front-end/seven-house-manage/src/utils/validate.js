/*
 * @Author: Topskys
 * @Date: 2022-10-19 17:38:12
 * @LastEditTime: 2022-11-08 15:53:26
 */
/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
/**
 * @description: 
 * @param {*} str
 * @return {*}
 */
export function validUsername(str) {
  const valid_map = ['1@qq.com', '123456', 'admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}


/**
 * @description: 手机号验证
 * @param {string} str
 * @return {Boolean}
 */
export const validatePhone = (str) => /^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(str.trim())