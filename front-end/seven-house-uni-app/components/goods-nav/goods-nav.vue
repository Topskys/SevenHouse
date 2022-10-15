<template>
	<view class="goods__nav r-flex-3" style="padding : 10px 15px; background-color: #333;">
		<view class="left">
			<uni-badge class="uni-badge-left-margin" :text="value" absolute="rightTop" size="small">
				<view class="fa-shopping-bag">
					<img src="static/images/shopping-bag.png" style="width:30px;height: 30px;">
				</view>
			</uni-badge>
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
				value: 7,
				// order: [{
				// 	id: 0,
				// 	name: '雪花·勇闯天涯',
				// 	price: 89,
				// 	count: 1,
				// }],
			}
		},
		computed: {
			...mapState({
				order: (state) => state.m_order.order
			})
		},
		methods: {
			// 加入购物车按钮的回调
			updateOrder() {
				this.$api.order.reqUpdateOrder(this.order).then(({
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
			navPayOrder: () => uni.navigateTo({
				url: `/pages/pay/pay?order=${this.order}`
			})
		},
		watch: {
			order: function() {
				this.value = this.order()
			}
		}
	}
</script>

<style>

</style>
