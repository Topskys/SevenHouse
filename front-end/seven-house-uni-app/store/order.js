export default {
	namespaced: true,

	state: () => ({
		// order: [{
		// 	id: 0,
		// 	name: '雪花·勇闯天涯',
		// 	price: 89,
		// 	count: 1,
		// }],
		order: JSON.parse(uni.getStorageSync("order") || '{}'),
	}),


	// 方法
	mutations: {

		// 更新用户的基本信息
		updateOrder(state, order) {
			state.order = order
			this.commit('m_order/saveOrderToStorage')
		},

		saveOrderToStorage(state) {
			uni.setStorageSync('order', JSON.stringify(state.order))
		},

		// 更新重定向的信息对象
		updateRedirectInfo(state, info) {
			state.redirectInfo = info
		},
	},

	getters: {},
}
