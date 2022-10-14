import App from './App'

// 引入全局样式
import '@/style/index.css'
// 引入求情函数
import api from '@/api/index.js'





// 挂载，扩展vue原型
Vue.prototype.$api = api


// 检查是否登录
// backpage, backtype 2个参数分别代表：
// backpage : 登录后返回的页面
// backtype : 打开页面的类型[1 : redirectTo 2 : switchTab]
Vue.prototype.$checkLogin = (backPath, backType) => {
	var token = uni.getStorageSync("token") || ""
	var userInfo = JSON.parse(uni.getStorageSync("userInfo") || '{}')
	if (token == "" || userInfo == {}) {
		uni.redirectTo({ // 只能打开非tabBar页面的路径
			url: `/pages/login/login?backPath=${backPath}&backType=${backType}`
		})
	}
}

// 插件配置
Vue.prototype.$getRect = function(selector, all) {
	return new Promise(resolve => {
		uni.createSelectorQuery().
		in(this)[all ? 'selectAll' : 'select'](selector)
			.boundingClientRect(rect => {
				if (all && Array.isArray(rect) && rect.length) {
					resolve(rect)
				}
				if (!all && rect) {
					resolve(rect)
				}
			})
			.exec()
	})
}





// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	...App
})
app.$mount()
// #endif

// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
export function createApp() {
	const app = createSSRApp(App)
	return {
		app
	}
}
// #endif
