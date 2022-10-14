<template>
	<scroll-view scroll-y="true" style="height: calc(100vh - 44px - 62.78px );  background-color: #eee;">
		<view class="swiper__container">
			<swiper circular :indicator-dots="true" :autoplay="true" :interval="2000" :duration="500"
				style="height: 180px;">
				<swiper-item v-for="(item,index) in banners" :key="index">
					<img :src="item.imgUrl" alt="404" style="width: 100%;height: 100%;" />
				</swiper-item>
			</swiper>
		</view>
		<uni-card :isShadow='false' style="border-radius: 20rpx;">
			<view>
				<!-- 点餐&存酒入口 -->
				<view class="r-flex-2">
					<view class="order__item c-flex-1" @click="swiPage('/pages/classify/classify')">
						<view class="order__up">
							<img src="static/images/bottle.png" />
						</view>
						<view class="order__down c-flex-1">
							<b class="order__title">点餐</b>
							<p class="order__slogan">一起吨吨吨</p>
						</view>
					</view>
					<view class="order__item  c-flex-1" @click="swiPage('/pages/classify/classify')">
						<view class="order__up">
							<img src="static/images/takeout.png" />
						</view>
						<view class="order__down  c-flex-1">
							<b class="order__title">存酒</b>
							<p class="order__slogan">把酒存起来</p>
						</view>
					</view>
				</view>
				<!-- 选座 -->
				<view class="order__seat">
					<picker @change="bindPickerChange" mode='selector' range-key="name" :value="index" :range="seat">
						<view class="picker" :style="{color:index===0?'#E6A23C':'#335eea'}">
							{{seat[index].name}}
						</view>
					</picker>
				</view>
				<!-- <view class="r-flex-2">
					<view class="extra__item c-flex-1" @click="rediPage('/pages/discount/discount')">
						<view class="extra__up">
							<img src="static/images/discount.png" />
						</view>
						<view class="extra__down c-flex-1">
							<b>优惠劵</b>
							<p class="extra__slogan">省钱小苗招</p>
						</view>
					</view>
					<view class="extra__item  c-flex-1" @click="rediPage('/pages/club/club')">
						<view class="extra__up">
							<img src="static/images/club.png" />
						</view>
						<view class="extra__down  c-flex-1">
							<b>部落</b>
							<p class="extra__slogan">海带聚集地</p>
						</view>
					</view>
				</view> -->
			</view>
		</uni-card>


		<!-- 会员新鲜事 -->
		<view class="news">
			<h4>会员新鲜事</h4>
			<view class="news__list">
				<view class="news__item" v-for="item,index in news" :key='index'>
					<a :href="item.url" target="_bank">
						<img :src="item.imgUrl " />
					</a>
				</view>
			</view>
		</view>

	</scroll-view>
</template>

<script>
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	export default {
		data() {
			return {
				index: 0,
				// 选座（或选包间）
				seat: [{
					id: 0,
					name: '您尚未选座？选座'
				}, {
					id: 1,
					name: '#001 座'
				}, {
					id: 2,
					name: '#002 座'
				}, {
					id: 3,
					name: '#003 座'
				}, {
					id: 4,
					name: '#004 座'
				}, {
					id: 5,
					name: '#005 包间'
				}],
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
						url: '',
					},
					{
						imgUrl: 'static/images/news1.png',
						url: '',
					},
					{
						imgUrl: 'static/images/news3.png',
						url: '',
					},
				]
			}
		},
		components: {
			uniCard,
		},
		onLoad() {

		},
		methods: {
			// 初始化首页数据
			// initHomeData() {
			// 	this.$api.home.reqHomeData().then(({
			// 		code,
			// 		data
			// 	}) => {
			// 		const {
			// 			banners,
			// 			seat,
			// 			news
			// 		} = data
			// 		code === 200 && ({
			// 			[this.banners, this.seat, this.news] = [banners, seat, news]
			// 		})
			// 	})
			// },

			// 监听顾客选择座位的回调
			bindPickerChange(e) {
				this.index = e.detail.value;
				// this.index !== 0 && setTimeout(() => {
				// 	this.$api.home.reqSetSeat().then(({
				// 		code,
				// 		data
				// 	}) => {
				// 		code === 200 && uni.showToast({
				// 			title: data.msg,
				// 			icon: 'none'
				// 		});
				// 	})
				// }, 1000)
			},

			// 页面跳转，uni.redirectTo()：只能打开非tabBar页面的路径
			rediPage: (path = '') => path && uni.redirectTo({
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
	.order__item {
		width: 50%;
		margin-top: 10px;

		.order__up {
			width: 80px;
			margin: 8px 0;
			text-align: center;

			img {
				width: 100%;
			}
		}

		.order__down {
			.order__title {
				color: #333;
				font-size: 20px;
			}

			.order__slogan {
				color: #aaa;
				font-size: 13px;
				margin-top: 5px;
			}
		}
	}

	.order__seat {
		margin-top: 20rpx;
		text-align: center;
	}

	.news {
		margin: 30rpx;

		.news__list {
			margin-top: 20rpx;

			.news__item {
				margin: 20rpx 0;
				z-index: 2;
				border-radius: 20rpx;
				// box-shadow: 0 2px 2px 0 #bbb;

				a>img {
					width: 100%;
					height: 100%;
					border-radius: 20rpx;
				}
			}
		}
	}

	@media screen and(min-width:540px) {
		.news>h4 {
			font-size: 24px
		}
	}



	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>
