<template>
	<view class="goods__nav r-flex-3" style="padding : 5px 15px; background-color: #333;width: 100vw;">
		<view class="left r-flex-5">
			<uni-badge class="uni-badge-left-margin" :text="$store.state.m_order.order.count" absolute="rightTop"
				size="small">
				<view class="fa-shopping-bag">
					<img src="static/images/shopping-bag.png" style="width:30px;height: 30px;">
				</view>
			</uni-badge>
			<!-- #e31d1a -->
			<text
				style="margin-left:10px;font-size: 14px;color: #dd524d;">{{this.$store.state.m_order.order.total===0?'':this.$store.state.m_order.order.total.toFixed(2)}}</text>
		</view>
		<view class="right">
			<button type='default' size='mini' style="color:#fff;background-color: #42b983 ;margin-right: 20rpx;"
				@click='updateOrder'>加入购物车</button>
			<button type='primary' size='mini' @click='navPayOrder'>立即购买</button>
		</view>
		<!-- <picker mode='selector' range-key="name" :range="order">
			<view class="picker r-flex-3">
				<view class="left">
					<uni-badge class="uni-badge-left-margin" :text="value" absolute="rightTop" size="small">
						<view class="fa-shopping-bag">
							<img src="static/images/shopping-bag.png" style="width:30px;height: 30px;">
						</view>
					</uni-badge>
				</view>
				<view class="right">
					<button type='default' size='mini'
						style="color:#fff;background-color: #42b983 ;margin-right: 20rpx;">加入购物车</button>
					<button type='primary' size='mini'>立即购买</button>
				</view>
			</view>
		</picker> -->
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import uniBadge from '@/components/uni-badge_1.2.1/components/uni-badge/uni-badge.vue'
	import uniPopup from '@/components/uni-popup_1.7.9/components/uni-popup/uni-popup.vue'
	export default {
		components: {
			uniBadge,
			uniPopup,
		},
		data() {
			return {
				// value: 0,
				// price: 0,
			}
		},
		computed: {
			...mapState({
				order: (state) => state.m_order.order
				// console.log(state.m_order.order)
			})
		},
		methods: {
			// 加入购物车按钮的回调
			updateOrder() {
				this.$api.order.reqUpdateOrder(this.$store.state.m_order.order).then(({
					code,
					data
				}) => {
					uni.showToast({
						title: data.msg,
						icon: code === 200 ? 'success' : 'error'
					})
				})
			},
			// 导航到支付订单页面
			navPayOrder: function() {
				this.updateOrder()
				uni.navigateTo({
					url: `/pages/pay/pay?orders=${encodeURIComponent(JSON.stringify(this.$store.state.m_order.order))}`,
				})
			},
			// 订单数据预处理
			// dealOrder(arr = []) {
			// 	[this.price, this.value] = [`${arr.reduce((sum, item) => sum += item.price, 0)}`,
			// 		`${arr.reduce((sum, item) => sum += item.count, 0)}`
			// 	]
			// },
		},
		// watch: {
		// 	order: function(newVal) {
		// 		console.log(":f")
		// 		this.dealOrder(newVal.list)
		// 	}
		// }
	}
</script>

<style>

</style>
