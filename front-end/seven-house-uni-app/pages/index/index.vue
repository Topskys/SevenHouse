<template>
	<scroll-view scroll-y="true" style="height: 100vh;  background-color: #eee;">
		<!-- 轮播 -->
		<view class="swiper__container">
			<swiper circular :indicator-dots="true" :autoplay="true" :interval="5000" :duration="1000"
				style="height: 400rpx;">
				<swiper-item v-for="(item,index) in banners" :key="index">
					<!-- <a href="#"></a> -->
					<image :src="item.imgUrl" mode="aspectFill" class="banner-img"></image>
				</swiper-item>
			</swiper>
		</view>
		<!-- 点餐&存酒入口 -->
		<view class="order__container">
			<view class="r-flex-2">
				<view class="order__item c-flex-1" @click="swiPage('/pages/classify/classify')">
					<view class="order__up">
						<img src="/static/images/bottle.png" class="order__img" />
					</view>
					<view class="order__down c-flex-1">
						<b class="order__title">点餐</b>
						<p class="order__slogan">一起吨吨吨</p>
					</view>
				</view>
				<view class="order__item  c-flex-1" @click="navPage('/pages/save-wine/save-wine')">
					<view class="order__up">
						<img src="/static/images/takeout.png" class="order__img" />
					</view>
					<view class="order__down  c-flex-1">
						<b class="order__title">取酒</b>
						<p class="order__slogan">把酒取出来</p>
					</view>
				</view>
			</view>
		</view>

		<!-- 会员新鲜事 -->
		<view class="news">
			<h3>会员新鲜事</h3>
			<view class="news__list">
				<view class="news__item" v-for="item,index in news" :key='index'>
					<a href="#" target="_blank">
						<img :src="item.imgUrl " class="news__img" />
					</a>
				</view>
			</view>
		</view>

	</scroll-view>
</template>

<script>
	export default {
		data() {
			return {
				// 顶部广告
				banners: [{
						imgUrl: '/static/images/banner1.png',
						url: "",
					},
					{
						imgUrl: '/static/images/banner2.png',
						url: "",
					},
					{
						imgUrl: '/static/images/banner2.png',
						url: "",
					},
				],
				// 底部新鲜趣事
				news: [{
						imgUrl: '/static/images/news2.png',
						url: '',
					},
					{
						imgUrl: '/static/images/news1.png',
						url: '',
					},
					{
						imgUrl: '/static/images/news3.png',
						url: '',
					},
				],
			}
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			this.$checkLogin("/pages/index/index", "2");
		},
		methods: {
			// // 初始化首页数据
			// initHomeData() {
			// 	this.$api.home.reqHomeData().then(({
			// 		code,
			// 		data
			// 	}) => {
			// 		let {
			// 			list
			// 		} = data
			// 		code === 200 && [list.map(item => item.name = item.deskNo), this.seat = list]
			// 	})
			// },
			// // 监听顾客选择座位的回调
			// bindPickerChange(e) {
			// 	var _this = this
			// 	_this.index = e.detail.value;
			// 	_this.index !== 0 && _this.$api.home.reqSetSeat({
			// 		..._this.seat[_this.index]
			// 	}).then(({
			// 		code,
			// 		data,
			// 		msg
			// 	}) => {
			// 		_this.initHomeData()
			// 		uni.showToast({
			// 			title: msg,
			// 			icon: code === 200 ? 'success' : 'error'
			// 		})
			// 	})
			// },
			// 页面跳转
			navPage: (path = '') => path && uni.navigateTo({
				url: path
			}),
			swiPage: (path = '') => path && uni.switchTab({
				url: path
			}),

			// 手机端才有的扫码接口
			// scanCode: () => {
			// 	// 允许从相机和相册扫码
			// 	uni.scanCode({
			// 		success: function(res) {
			// 			console.log('条码类型：' + res.scanType);
			// 			console.log('条码内容：' + res.result);
			// 		}
			// 	})
			// },
		}
	}
</script>

<style lang="scss" scoped>
	// if you 使用的是 less 请先安装 less、less-loader
	// 使用 rpx单位需注意屏幕变化元素自动缩放的问题
	.banner-img {
		width: 100%;
		height: 100%;
	}

	.order__container {
		margin: 40rpx 30rpx;
		background-color: #fff;
		border-radius: 20rpx;
		box-shadow: 0rpx 0rpx 10rpx 5rpx #cccccc;

		.order__item {
			width: 50%;
			margin: 20rpx 0 40rpx 0;

			.order__up {
				width: 250rpx;
				height: 250rpx;
				margin: 16rpx 0;
				text-align: center;

				.order__img {
					width: 100%;
					height: 100%;
				}
			}

			.order__down {
				.order__title {
					color: #000;
					font-size: 40rpx;
					font-weight: 550;
				}

				.order__slogan {
					color: #999;
					font-size: 26rpx;
					margin-top: 10rpx;
				}
			}
		}
	}

	.news {
		color: #000;
		font-size: 40rpx;
		font-weight: 550;
		margin: 30rpx 30rpx 40rpx 30rpx;

		.news__list {
			margin-top: 20rpx;

			.news__item {
				margin: 20rpx 0;
				z-index: 10;
				border-radius: 20rpx;
				overflow: hidden;

				.news__img {
					width: 100%;
					height: 350rpx;
					border-radius: 20rpx;
				}
			}
		}
	}
</style>
