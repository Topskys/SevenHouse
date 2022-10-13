<template>
	<scroll-view scroll-y="true" style="height: calc(100vh - 44px - 62.78px );  background-color: #eee;">
		<view class="swiper__container">
			<swiper circular :indicator-dots="true" :autoplay="true" :interval="2000" :duration="500"
				style="height: 200px;">
				<swiper-item v-for="(item,index) in banners" :key="index">
					<img :src="item.imgUrl" alt="404" style="width: 100%;height: 100%;" />
				</swiper-item>
			</swiper>
		</view>
		<view style="margin: 0 40rpx 40rpx 40rpx;background-color:#fff;border-radius: 10px;padding-bottom:20px;">
			<view class="r-flex-2">
				<view class="order__item c-flex-1">
					<view class="order__up">
						<img src="static/images/bottle.png" />
					</view>
					<view class="order__down c-flex-1">
						<b>点餐</b>
						<p class="order__slogan">一起吨吨吨</p>
					</view>
				</view>
				<view class="order__item  c-flex-1">
					<view class="order__up">
						<img src="static/images/takeout.png" />
					</view>
					<view class="order__down  c-flex-1">
						<b>商城</b>
						<p class="order__slogan">把酒带回家</p>
					</view>
				</view>
			</view>
			<view class="r-flex-2">
				<view class="extra__item c-flex-1">
					<view class="extra__up">
						<img src="static/images/discount.png" />
					</view>
					<view class="extra__down c-flex-1">
						<b>优惠劵</b>
						<p class="extra__slogan">省钱小苗招</p>
					</view>
				</view>
				<view class="extra__item  c-flex-1">
					<view class="extra__up">
						<img src="static/images/club.png" />
					</view>
					<view class="extra__down  c-flex-1">
						<b>部落</b>
						<p class="extra__slogan">海带聚集地</p>
					</view>
				</view>
			</view>
		</view>


		<view class="news" style="margin: 40rpx;">
			<h4>会员新鲜事</h4>
			<view class="news__list" style="margin-top: 20rpx;">
				<view class="news__item" v-for="item,index in news" :key='index' style="margin: 20rpx 0;">
					<img :src="item.imgUrl " style="width: 100%; border-radius: 20rpx;" />
				</view>
			</view>
		</view>

	</scroll-view>
</template>

<script>
	export default {
		data() {
			return {
				banners: [{
						imgUrl: 'static/images/banner1.png',
					},
					{
						imgUrl: 'static/images/banner2.png',
					},
					{
						imgUrl: 'static/images/banner2.png',
					},
				],
				news: [{
						imgUrl: 'static/images/news2.png',
					},
					{
						imgUrl: 'static/images/news1.png',
					},
					{
						imgUrl: 'static/images/news3.png',
					},
				]
			}
		},
		onLoad() {

		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.order__item {
		width: 50%;
		margin-top: 40rpx;

		.order__up {
			width: 160rpx;
			margin: 10rpx 0;
			text-align: center;

			img {
				width: 100%;
			}
		}

		.order__down {
			font-size: 40rpx;

			.order__slogan {
				color: #aaa;
				font-size: 26rpx;
				margin-top: 10rpx;
			}
		}
	}

	.extra__item {
		width: 50%;
		margin-top: 60rpx;

		.extra__up {
			width: 70rpx;
			margin: 5rpx 0;
			text-align: center;

			img {
				width: 100%;
			}
		}

		.extra__down {
			.extra__slogan {
				color: #aaa;
				font-size: 26rpx;
			}
		}
	}

	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
