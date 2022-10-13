<template>
	<view>
		<liuyuno-tabs :tabData="tabs" :defaultIndex="defaultIndex" @tabClick='tabClick' :config='config' />
		<scroll-view v-show="defaultIndex==0" scroll-y="true" style="height: calc(100vh - 44px - 62.78px - 45px); ">
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in 10" :key='item'>
				<view class="order__item">
					<view class="order__header r-flex-3" style="padding-bottom:20rpx ;border-bottom: 1px solid #eee;">
						<view class="header__left">订单编号：X1234568898098</view>
						<view class="header__right" style="color: #335eea;">待付款</view>
					</view>
					<view class="order__body r-flex-5" style="margin-top: 20rpx ;">
						<view class="body__left" style="margin-right: 20rpx;">
							<img src="static/images/banner1.png"
								style='width: 150rpx; height: 150rpx; border-radius: 10rpx;' />
						</view>
						<view class="body__right" style="flex: 1;">
							<b
								style='color: #000;width:100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>贵州茅台酒套餐</b>
							<view class="r-flex-3">
								<p style="font-size: 24rpx;">下单时间：2022-10-13</p>
								<p style="font-size: 24rpx;">x1</p>
							</view>
							<view class="body__price r-flex-5" style="color: #000;">
								优惠:<p style='margin-right: 20rpx;'>10元</p>
								实付:<p style='color: orangered;font-size: 36rpx;'>￥520.00</p>
							</view>
						</view>
					</view>
					<view class="order__footer  r-flex-6" style="margin-top: 40rpx ;">
						<view
							style="border: 1px solid #335eea; color:#335eea;font-size: 28rpx;padding:0 20rpx;line-height: 2;border-radius: 10rpx;margin-right:20rpx;">
							取消订单</view>
						<view
							style="border: 1px solid #335eea; color:#fff;font-size: 28rpx;padding:0 20rpx;line-height: 2;border-radius: 10rpx;background-color: #335eea;">
							立即付款</view>
					</view>
				</view>
			</uni-card>

		</scroll-view>

		<scroll-view v-show="defaultIndex==1" scroll-y="true" style="height: calc(100vh - 44px - 62.78px - 45px); ">
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in 2" :key='item'>
				<view class="order__item">
					<view class="order__header r-flex-3" style="padding-bottom:20rpx ;border-bottom: 1px solid #eee;">
						<view class="header__left">订单编号：X1234568898098</view>
						<view class="header__right" style="color: #335eea;">交易成功</view>
					</view>
					<!-- 右滑删除 -->
					<!-- <uni-swipe-action></uni-swipe-action> -->

					<view class="order__body r-flex-5" style="margin-top: 20rpx ;" @click="contentClick">
						<view class="body__left" style="margin-right: 20rpx;">
							<img src="static/images/banner1.png"
								style='width: 150rpx; height: 150rpx; border-radius: 10rpx;' />
						</view>
						<view class="body__right" style="flex: 1;">
							<b
								style='color: #000;width:100%;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;'>贵州茅台酒套餐</b>
							<view class="r-flex-3">
								<p style="font-size: 24rpx;">下单时间：2022-10-13</p>
								<p style="font-size: 24rpx;">x1</p>
							</view>
							<view class="body__price r-flex-5" style="color: #000;">
								优惠:<p style='margin-right: 20rpx;'>10元</p>
								实付:<p style='color: orangered;font-size: 36rpx;'>￥520.00</p>
							</view>
						</view>
					</view>
					<view class="order__footer  r-flex-6" style="margin-top: 40rpx ;">
						<!-- <view
							style="border: 1px solid  orangered; color:#fff;font-size: 28rpx;padding:0 20rpx;line-height: 2;border-radius: 10rpx;background-color: orangered;">
							删除</view> -->
						<view><button type="warn" size='mini' @click="del(item)">删除</button></view>
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
				order: [{
					title: 'yy',
				}],
			}
		},
		components: {
			liuyunoTabs,
			uniCard,
		},
		methods: {
			// tabs的回调
			tabClick(val) {
				this.defaultIndex = val
			},
			// 删除历史订单
			del(item) {
				console.log(item)
			}
		}
	}
</script>

<style lang="scss" scoped>
	uni-card:nth-last-child() {
		margin-bottom: 20px;
	}


	.content-box {
		flex: 1;
		/* #ifdef APP-NVUE */
		justify-content: center;
		/* #endif */
		height: 44px;
		line-height: 44px;
		padding: 0 15px;
		position: relative;
		background-color: #fff;
		border-bottom-color: #f5f5f5;
		border-bottom-width: 1px;
		border-bottom-style: solid;
	}
</style>
