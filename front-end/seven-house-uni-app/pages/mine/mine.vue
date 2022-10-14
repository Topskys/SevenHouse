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
						<b style='color:#000; font-size:36rpx' @click='rediPage'>登录</b>
						<p style='opacity: .8; font-size:28rpx'>ID:0000001</p>
					</view>
				</view>
				<view class="info__right" style="color: darkgoldenrod;font-size: 36rpx;">LV1</view>
			</view>
			<view class="customer__last__eine" style="margin-top: 20rpx;">
				<p>存酒余量</p>
				<progress :percent="last" show-info active />
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
				<view class="server__item c-flex-2" v-for="item,index in server" :key="item.title"
					@click="rediPage(item)">
					<img :src="item.icon" style='width: 60rpx;height: 60rpx;' />
					<span style='font-size: 28rpx;margin-top: 10rpx;'>{{item.title}}</span>
				</view>
			</view>
		</uni-card>


		<view class="goods__nav r-flex-3" style="margin: 0 40rpx;">
			<view class="left">
				<uni-badge class="uni-badge-left-margin" :text="value" absolute="rightTop" size="small">
					<view class="fa-shopping-bag" @click="spreadOrder('bottom')">
						<img src="static/images/shopping-bag.png" style="width:60rpx;height: 60rpx;">
					</view>
				</uni-badge>
			</view>
			<view class="right">
				<button type='default' size='mini'
					style="color:#fff;background-color: #42b983 ;margin-right: 20rpx;">加入购物车</button>
				<button type='primary' size='mini'>立即购买</button>
			</view>
			<uni-popup ref="popup" background-color="#fff" @change="change" style="height: 200px;">
				底部弹出 Popup5332464
			</uni-popup>
		</view>
	</scroll-view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	import uniBadge from '@/components/uni-badge_1.2.1/components/uni-badge/uni-badge.vue'
	import uniPopup from '@/components/uni-popup_1.7.9/components/uni-popup/uni-popup.vue'
	export default {
		data() {
			return {
				type: 'bottom',
				value: 7,
				last: 45, // 存酒余量
				server: [{
						icon: "static/images/club.png",
						title: '官方部落',
						path: "/pages/club/club",
					},
					{
						icon: "static/images/database.png",
						title: '我的存酒',
						path: "/pages/club/club",
					},
					{
						icon: "static/images/info.png",
						title: '个人信息',
						path: "/pages/selfInfo/selfInfo",
					},
					{
						icon: "static/images/market.png",
						title: '官方商城',
						path: "/pages/club/club",
					},
					{
						icon: "static/images/more.png",
						title: '更多服务',
						path: "/pages/club/club",
					}
				]
			}
		},
		components: {
			uniCard,
			uniBadge,
			uniPopup,
		},
		computed: {
			...mapState({
				userInfo: (state) => state.m_user.userInfo
			})
		},
		methods: {
			// 页面跳转，uni.redirectTo()：只能打开非tabBar页面的路径
			rediPage: ({
				path = ''
			}) => uni.redirectTo({
				url: path ? path : `/pages/login/login`
			}),
			spreadOrder(type) {
				this.type = type
				// open 方法传入参数 等同在 uni-popup 组件上绑定 type属性
				this.$refs.popup.open(type)

			},
			close() {
				this.$refs.popup.close()
			},
			change(e) {
				console.log('当前模式：' + e.type + ',状态：' + e.show);
			},
		}
	}
</script>

<style>

</style>
