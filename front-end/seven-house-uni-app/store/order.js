import Vue from 'vue'
export default {
	namespaced: true,

	state: () => ({
		order: {
			shopCart: [], // 购物车
			total: 0,
			count: 0,
		},
		spread: false,
	}),
	// 方法
	mutations: {
		// 更新购物车
		updateShopCart(state, goods) {
			// 根据提交的商品的Id，查询购物车中是否存在这件商品
			// 如果不存在，则 findResult 为 undefined；否则，为查找到的商品信息对象
			let {
				shopCart
			} = state.order;
			for (let i = 0; i < shopCart.length; i++) {
				if (shopCart[i].alcoId === goods.alcoId && shopCart[i].skuId == goods.sku_id) {
					let stock = shopCart[i].alcoholsSkuList[shopCart[i].skuId - 1].skuStock
					shopCart[i].num >= stock ? (shopCart[i].num = stock) : (shopCart[i].num += goods.number);
					// shopCart[i].num += goods.number;
					this.commit("m_order/sumProduct");
					return;
				}
			}
			Vue.set(goods, "num", goods.number);
			Vue.set(goods, "skuId", goods.sku_id);
			state.order.shopCart.push(goods);
			this.commit("m_order/sumProduct");
			console.log("updateShopCart+：", shopCart, state.order.shopCart)
			// let findResult = state.order.shopCart.find((item) => item.alcoId === goods.alcoId)
			// if (!findResult) {
			// 	state.order.shopCart.push(goods);
			// } else {
			// 	let findResult1 = state.order.shopCart.find((item) => item.skuId === goods.skuId)
			// 	if (findResult1) { // findResult.skuId == goods.skuId
			// 		// 如果购物车中有这件商品，则更新数量和规格即可
			// 		findResult.num += goods.num;
			// 		findResult.skuId = goods.skuId;
			// 	} else {
			// 		state.order.shopCart.push(goods);
			// 	}
			// }

		},
		addCart(state, {
			alcoId,
			which,
			skuId,
		}) {
			let {
				shopCart
			} = state.order;
			for (let i = 0; i < shopCart.length; i++) {
				if (shopCart[i].alcoId == alcoId && shopCart[i].skuId == skuId) {
					if (which == "add") {
						// shopCart[i].num++;
						let stock = shopCart[i].alcoholsSkuList[shopCart[i].skuId - 1].skuStock
						shopCart[i].num >= stock ? (shopCart[i].num = stock) : shopCart[i].num++;
					} else {
						shopCart[i].num > 0 && shopCart[i].num--;
						shopCart[i].num == 0 && shopCart.splice(i, 1)
					}
					break;
				}
			}
			this.commit("m_order/sumProduct");
			// console.log("shopCart-addCart：", state.order.shopCart)
		},
		// 就和
		sumProduct(state) {
			state.order.total = state.order.shopCart.reduce((sum, item) => sum += item.alcoholsSkuList[item.skuId - 1]
				.skuSellprice * item.num, 0);
			state.order.count = state.order.shopCart.reduce((sum, item) => sum += item.num, 0);
			this.commit("m_order/saveProductToStorage");
		},
		saveProductToStorage(state) {
			uni.setStorageSync('order', JSON.stringify(state.order))
		},
		getProductFromStorage(state) {
			state.order = JSON.parse(uni.getStorageSync('order') || JSON.stringify(state.order))
		},
		// 本地存储订单
		saveOrderToStorage(state) {
			uni.setStorageSync('order', JSON.stringify(state.order))
		},
		// 删除本地订单
		deleteOrder(state) {
			uni.removeStorageSync('order');
			state.order.shopCart = []; // 购物车
			state.order.total = 0;
			state.order.count = 0;
			// console.log("清空购物车：", state.order)
		},
		// 更新重定向的信息对象
		updateRedirectInfo(state, info) {
			state.redirectInfo = info
		},
		// 展开购物袋 
		updateSpread(state) {
			state.spread = !state.spread
		}
	},

	getters: {},
}
