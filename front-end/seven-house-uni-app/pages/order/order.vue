<template>
	<view>
		<liuyuno-tabs :tabData="tabs" :defaultIndex="defaultIndex" @tabClick='tabClick' :config='config' />
		<scroll-view v-show="defaultIndex==0" scroll-y="true" class="scroll">
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in 10" :key='item'>
				<view class="order__item">
					<view class="order__header r-flex-3">
						<view class="header__left">订单编号：X1234568898098{{item.isbn}}</view>
						<view class="header__right">待付款{{item.status}}</view>
					</view>
					<view class="order__body r-flex-1 mt10">
						<view class="body__left">
							<img src="static/images/banner1.png" />
						</view>
						<view class="body__right" style="flex: 1;">
							<b class='order__title'>贵州茅台酒套餐{{item.name}}</b>
							<view class="r-flex-3">
								<p style="font-size: 12px;">下单时间：{{item.time}}</p>
								<p style="font-size: 12px;">x1{{item.count}}</p>
							</view>
							<view class="body__price r-flex-5" style="color: #000;">
								<span>优惠：</span><span style='margin-right: 10px;'>{{item.discount}}0元</span>
								<span>实付：￥</span><span
									style='color: orangered;font-size: 18px;'>{{item.price}}3299元</span>
							</view>
						</view>
					</view>
					<view class="order__footer  r-flex-6 mt10">
						<view>
							<button type="default" size="mini"
								style="margin-right: 10px;border: 1px solid #335eea;background-color: #fff;"
								@click='cancel(item)'>取消订单</button>
							<button type="primary" size="mini" @click='navPage("/pages/pay/pay")'>立即付款</button>
						</view>
						<!-- <view
							style="border: 1px solid #335eea; color:#335eea;font-size: 28rpx;padding:0 20rpx;line-height: 2;border-radius: 10rpx;margin-right:20rpx;">
							取消订单</view>
						<view
							style="border: 1px solid #335eea; color:#fff;font-size: 28rpx;padding:0 20rpx;line-height: 2;border-radius: 10rpx;background-color: #335eea;">
							立即付款</view> -->
					</view>
				</view>
			</uni-card>

		</scroll-view>

		<scroll-view v-show="defaultIndex==1" scroll-y="true" class='scroll'>

			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in 2" :key='item'>
				<view class="order__item">
					<view class="order__header r-flex-3">
						<view class="header__left">订单编号：X1234568898098{{item.isbn}}</view>
						<view class="header__right">交易成功{{item.status}}</view>
					</view>
					<view class="order__body r-flex-1 mt10">
						<view class="body__left">
							<img src="static/images/banner1.png" />
						</view>
						<view class="body__right" style="flex: 1;">
							<b class='order__title'>贵州茅台酒套餐{{item.name}}</b>
							<view class="r-flex-3">
								<p style="font-size: 12px;">下单时间：{{item.time}}</p>
								<p style="font-size: 12px;">x1{{item.count}}</p>
							</view>
							<view class="body__price r-flex-5" style="color: #000;">
								<span>优惠：</span><span style='margin-right: 10px;'>{{item.discount}}0元</span>
								<span>实付：￥</span><span
									style='color: orangered;font-size: 18px;'>{{item.price}}3299元</span>
							</view>
						</view>
					</view>
					<view class="order__footer  r-flex-6 mt10">
						<view><button type="warn" size='mini' @click="del(item)">删除订单</button></view>
					</view>
				</view>
			</uni-card>
		</scroll-view>

	</view>
</template>

<script>
	import liuyunoTabs from '@/common/liuyuno-tabs_2.0.2/components/liuyuno-tabs/liuyuno-tabs.vue'
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	export default {
		data() {
			return {
				tabs: ['订单', '历史订单'],
				defaultIndex: 0,
				config: {
					activeColor: '#335eea',
					underLineColor: '#335eea',
				},
				order: {
					newOrder: [],
					oldOrder: [],
				},
			}
		},
		components: {
			liuyunoTabs,
			uniCard,
		},
		methods: {
			// 获取订单
			getOrderList() {
				this.$api.reqOrderList().then(({
					code,
					data
				}) => {
					uni.showToast({
						title: data.msg,
						icon: code === 200 ? () => {
							const {
								order
							} = data.order
							let [newOrder, oldOrder] = [
								[],
								[]
							]
							newOrder = order.filter(item => {
								if (item.isNew) {
									return item
								} else {
									oldOrder.push(item)
								}
							})[this.order.newOrder, this.order.oldArr] = [newOrder, oldOrder]
							return 'success'
						} : 'error'
					})
				})
			},
			// tabs的回调
			tabClick(val) {
				this.defaultIndex = val
			},
			// 取消订单按钮的回调
			cancel(item) {
				this.$api.reqDelOrder(item).then(({
					code,
					data
				}) => {
					uni.showToast({
						title: data.msg,
						icon: code === 200 ? () => {
							this.getOrderList()
							return 'success'
						} : 'error'
					})
				})
			},
			// 立即支付按钮的回调
			navPage: (path = '') => uni.navigateTo({
				url: path
			}),
			// 删除历史订单
			del(item) {
				this.$api.reqDelOrder(item).then(({
					code,
					data
				}) => {
					uni.showToast({
						title: data.msg,
						icon: code === 200 ? () => {
							this.getOrderList()
							return 'success'
						} : 'error'
					})
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.scroll {
		height: calc(100vh - 44px - 62.78px - 45px);

	}

	.order__item {
		.order__header {
			padding-bottom: 10px;
			border-bottom: 1px solid #eee;

			.header__right {
				color: var(--primary-color);
			}
		}

		.order__body {
			.body__left {
				margin-right: 10px;

				img {
					width: 75px;
					height: 75px;
					border-radius: 5px;
				}
			}
		}
	}

	.order__title {
		color: #000;
		font-size: 16px;
	}
</style>
