<template>
	<scroll-view scroll-y="true"
		style="height: calc(100vh - 44px - 62.78px ); background-image: linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%);">
		<uni-card :isShadow="false" style='border-radius: 20rpx;margin-top:31%;'>
			<view class="customer__info  r-flex-3">
				<view class="info__left r-flex-1">
					<view class="customer__avatar" style="width: 50px;height:50px;margin-right: 20rpx;">
						<img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
							style="border-radius:50%; width: 100%;height: 100%;">
					</view>
					<view>
						<b style='color:#000; font-size:36rpx' @click='rediLogin'>登录</b>
						<p style='opacity: .8; font-size:28rpx'>ID:0000001</p>
					</view>
				</view>
				<view class="info__right" style="color: darkgoldenrod;font-size: 36rpx;">LV1</view>
			</view>
		</uni-card>
		<uni-card :isShadow="false" style='border-radius: 20rpx;'>
			<view class="score__discount r-flex-4">
				<view class="customer__score" style="width: 50%;">
					<h4 style='color:#000;'>我的积分</h4>
					<b style='color: #000;font-size:32rpx;line-height:80rpx;'>0</b>
					<p style='opacity:.8 ;font-size: 24rpx;'>快来用积分兑换您喜欢的宝贝吧~</p>
				</view>
				<view class="customer__score" style="width: 50%;">
					<h4 style='color:#000;'>优惠劵</h4>
					<b style='color: #000;font-size:32rpx;line-height:80rpx;'>0</b>
					<p style='opacity:.8 ;font-size: 24rpx;'>快来使用优惠劵吧~</p>
				</view>
			</view>
		</uni-card>
		<uni-card :isShadow="false" style='border-radius: 20rpx;'>
			<h4 style='color:#000;'>我的服务</h4>
			<view class="server__list"
				style="margin: 20rpx 0;display: grid;grid-template-columns: repeat(4,1fr);gap: 40rpx;">
				<view class="server__item c-flex-2" v-for="item,index in server" :key="item.title">
					<img :src="item.icon" style='width: 60rpx;height: 60rpx;' />
					<span style='font-size: 28rpx;margin-top: 10rpx;'>{{item.title}}</span>
				</view>
			</view>
		</uni-card>
	</scroll-view>
</template>

<script>
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	export default {
		data() {
			return {
				server: [{
						icon: "static/images/club.png",
						title: '官方部落'
					},
					{
						icon: "static/images/database.png",
						title: '我的存酒'
					},
					{
						icon: "static/images/info.png",
						title: '个人信息'
					},
					{
						icon: "static/images/market.png",
						title: '官方商城'
					},
					{
						icon: "static/images/more.png",
						title: '更多服务'
					}
				]
			}
		},
		components: {
			uniCard,
		},
		methods: {
			// 前往登录页
			// uni.redirectTo()：只能打开非tabBar页面的路径
			rediLogin: () => uni.redirectTo({
				url: `/pages/login/login`
			})
		}
	}
</script>

<style>

</style>
