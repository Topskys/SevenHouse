export default {
	namespaced: true,

	state: () => ({
		// order: JSON.parse(uni.getStorageSync("order") || '[]'),
		order: {
			list: [],
			total: 0,
			count: 0,
			// {
			// id: 6,
			// orderId: '',
			// discount: 0,
			// status: "",
			// price: 3299,
			// payPrice: 0 || 3299,
			// name: "贵州茅台酒套餐",
			// time: '',
			// save: false, // 0 || 1
			// pay: false,
			// imgUrl: "/static/images/banner1.png",
			// delete: false,
			// count: 0, // 订购数量
			// payWay: "",
			// stock: 100, //库存
			// description: "简要描述",
			// classification: {
			// 	index: 0,
			// 	title: "该商品所属的类别"
			// }
			// },
		}

	}),


	// 方法
	mutations: {

		// 更新用户的基本信息
		updateOrder(state, obj) {
			// state.order.count+=item.count
			state.order.list.length ? state.order.list.forEach(item => {
				(item.id === obj.id) ? (item.price += obj.price, item.count = obj.count) :
				state.order.list.push(obj)
				// 容易理解的写法
				// if (item.id === obj.id) {
				// 	item.payPrice += obj.payPrice
				// 	item.count = obj.count
				// } else {
				// 	state.order.push(obj)
				// }
			}) : state.order.list.push(obj)
			state.order.total = state.order.list.reduce((sum, item) => sum += item.price, 0)
			state.order.count = state.order.list.reduce((sum, item) => sum += item.count, 0)
			// console.log(state.order.total, state.order.count)
			this.commit('m_order/saveOrderToStorage')
		},

		saveOrderToStorage(state) {
			uni.setStorageSync('order', JSON.stringify(state.order))
		},


		// 删除本地订单
		deleteOrder() {
			uni.removeStorageSync('order')
		},

		// 更新重定向的信息对象
		updateRedirectInfo(state, info) {
			state.redirectInfo = info
		},
	},

	getters: {},
}
