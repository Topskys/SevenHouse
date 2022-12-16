<template>
	<view>
		<liuyuno-tabs :tabData="tabs" :defaultIndex="defaultIndex" @tabClick='tabClick' :config='config' />
		<scroll-view v-show="defaultIndex==0" scroll-y="true" class="scroll">
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item,i in newOrder" :key='i'>
				<view class="order__item">
					<view class="order__header r-flex-3">
						<view class="header__left">订单编号：{{item.id}}</view>
						<view class="header__right">{{item.status}}</view>
					</view>
					<view class="order__body r-flex-1 mt10" @click="navTo(item,2)">
						<view class="body__left">
							<img :src="item.orderItemList.length>0?item.orderItemList[0].alcoImage:''" />
						</view>
						<view class="body__right" style="flex: 1;">
							<b class='order__title'>{{item.name}}</b>
							<view class="r-flex-3">
								<p style="font-size: 24rpx;margin: 10rpx 0;">下单时间：{{item.createTime }}</p>
								<p style="font-size: 24rpx;">x{{item.count}}</p>
							</view>
							<view class="body__price r-flex-5" style="color: #000;">
								<span>优惠：</span><span
									style='margin-right: 20rpx;'>{{item.discount?item.discount:0}}元</span>
								<span>实付：￥</span><span style='color: orangered;font-size: 36rpx;'>{{item.total}}元</span>
							</view>
						</view>
					</view>
					<view class="order__footer  r-flex-6 mt10" v-show="item.status=='未支付'?true:false">
						<view>
							<button type="default" size="mini"
								style="margin-right: 10px;border: 1px solid #335eea;background-color: #fff;"
								@click='cancel(item)'>取消订单</button>
							<button type="primary" size="mini" @click='navTo(item,1)'>
								立即付款
							</button>
						</view>
					</view>
				</view>
			</uni-card>

		</scroll-view>

		<scroll-view v-show="defaultIndex==1" scroll-y="true" class='scroll'>
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item,i in oldOrder" :key='i'>
				<view class="order__item">
					<view class="order__header r-flex-3">
						<view class="header__left">订单编号：{{item.id}}</view>
						<view class="header__right">{{item.status}}</view>
					</view>
					<view class="order__body r-flex-1 mt10">
						<view class="body__left">
							<!-- <img :src="item.orderItemList[0].alcoImage" /> -->
							<img :src="item.orderItemList.length>0?item.orderItemList[0].alcoImage:''" />
						</view>
						<view class="body__right" style="flex: 1;">
							<b class='order__title'>{{item.name}}</b>
							<view class="r-flex-3">
								<p style="font-size: 24rpx;margin: 10rpx 0;">下单时间：{{item.createTime }}</p>
								<p style="font-size: 24rpx;">x{{item.count}}</p>
							</view>
							<view class="body__price r-flex-5" style="color: #000;">
								<span>优惠：</span><span style='margin-right: 20rpx;'>0元</span>
								<span>实付：￥</span><span style='color: orangered;font-size: 36rpx;'>{{item.total}}元</span>
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
				newOrder: [],
				oldOrder: [],
			}
		},
		components: {
			liuyunoTabs,
			uniCard,
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			this.$checkLogin("/pages/order/order", "2");
			uni.startPullDownRefresh();
		},
		onShow() {
			this.getOrderList();
		},
		onPullDownRefresh() {
			this.getOrderList();
			uni.stopPullDownRefresh();
		},
		methods: {
			// 获取订单
			getOrderList() {
				this.$api.order.reqOrderList().then(({
					code,
					data,
					msg
				}) => {
					if (code === 200) {
						this.newOrder = {
							...data.newOrder
						};
						this.oldOrder = {
							...data.oldOrder
						};
					} else {
						uni.showToast({
							title: msg,
							icon: 'error'
						})
					}
				})
			},
			// tabs的回调
			tabClick(val) {
				this.defaultIndex = val
			},
			// 取消订单按钮的回调
			cancel(item) {
				this.$api.order.reqCancelOrder(item.id).then(({
					code,
					data,
					msg
				}) => {
					code === 200 && this.getOrderList()
					uni.showToast({
						title: msg,
						icon: code === 200 ? 'success' : 'error'
					})
				})
			},
			// 立即支付按钮及订单详情的回调  微信拿不到item对象，是编译问题（key），详情：https://ask.dcloud.net.cn/question/113818
			navTo: (item, where) => {
				uni.navigateTo({
					url: `/pages/${where===1?"pay/pay":"order/order-detail"}?order=${encodeURIComponent(JSON.stringify(item))}`,
				})
			},
			// 删除历史订单
			// 取消订单按钮的回调
			del(item) {
				this.$api.order.reqDelOrder(item.id).then(({
					code,
					data,
					msg
				}) => {
					code === 200 && this.getOrderList()
					uni.showToast({
						title: msg,
						icon: code === 200 ? 'success' : 'error'
					})
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.scroll {
		// height: calc(100vh - 44px - 62.78px - 45px);
		height: 100vh;
	}

	.order__item {
		.order__header {
			padding-bottom: 20rpx;
			border-bottom: 1px solid #eee;

			.header__right {
				color: var(--primary-color);
			}
		}

		.order__body {
			.body__left {
				margin-right: 20rpx;

				img {
					width: 150rpx;
					height: 150rpx;
					border-radius: 10rpx;
				}
			}
		}
	}

	.order__title {
		color: #000;
		font-size: 32rpx;
	}
</style>
