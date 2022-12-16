export default {
	// 开启命名空间
	namespaced: true,

	// 数据
	state: () => ({
		userInfo: JSON.parse(uni.getStorageSync("userInfo") || '{}'),
		// openId: uni.getStorageSync('openId') || "",
		token: uni.getStorageSync('token') || "",
		redirectInfo: null, // 重定向的 object 对象 { openType, from },
		list: []
	}),


	// 方法
	mutations: {

		// 更新用户的基本信息
		updateUserInfo(state, userInfo) {
			state.userInfo = userInfo
			// 通过 this.commit() 方法，调用 m_user 模块下的 saveUserInfoToStorage 方法，将 userinfo 对象持久化存储到本地
			this.commit('m_user/saveUserInfoToStorage') // 定义调用模块名字：m_user/方法名
		},

		// 将 userinfo 持久化存储到本地
		saveUserInfoToStorage(state) {
			uni.setStorageSync('userInfo', JSON.stringify(state.userInfo))
		},

		// 删除用户信息，重新登录
		deleteUserInfo(state) {
			uni.removeStorageSync('userInfo');
			state.userInfo = '';
		},


		// 更新 token 字符串
		updateToken(state, token) {
			state.token = token
			// 通过 this.commit() 方法，调用 m_user 模块下的 saveTokenToStorage 方法，将 token 字符串持久化存储到本地
			this.commit('m_user/saveTokenToStorage')
		},

		// 将 token 字符串持久化存储到本地
		saveTokenToStorage(state) {
			uni.setStorageSync('token', state.token)
		},

		// 删除用户信息，重新登录
		deleteToken() {
			uni.removeStorageSync('token')
		},


		// 更新重定向的信息对象
		updateRedirectInfo(state, info) {
			state.redirectInfo = info
		},
	},

	getters: {},
}
