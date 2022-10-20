/*
 * @Author: Topskys
 * @Date: 2022-05-21 22:24:00
 * @LastEditTime: 2022-10-07 10:47:20
 */
import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import user from './modules/user'
import home from './modules/home'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    settings,
    user,
    home
  },
  getters
})

export default store
