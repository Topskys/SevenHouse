// 1. 导入 Vue 和 Vuex
import Vue from 'vue'
import Vuex from 'vuex'

// 导入用户vuex模块
import mUser from '@/store/user.js'

// 2. 将 Vuex 安装为 Vue 的插件
Vue.use(Vuex)

// 3. 创建 Store 的实例对象
const store = new Vuex.Store({
	// TODO：挂载 store 模块
	modules: {
		// 4. 挂载购物车的 vuex 模块，模块内成员的访问路径被调整为 m_user，例如：
		//   用户模块中 user 数组的访问路径是 m_user/ways
		m_user: mUser,
	},
})

// 4. 向外共享 Store 的实例对象
export default store
