<template>
	<scroll-view scroll-y="true" style="height: calc(100vh - 44px - 62.78px );  background-color: #eee;">
		<!-- 轮播 -->
		<view class="swiper__container">
			<swiper circular :indicator-dots="true" :autoplay="true" :interval="2000" :duration="500"
				style="height: 180px;">
				<swiper-item v-for="(item,index) in banners" :key="index">
					<a :href="item.url" target="_blank"><img :src="item.imgUrl" alt="404"
							style="width: 100%;height: 100%;" /></a>
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
					<view class="order__item  c-flex-1" @click="navPage('/pages/save-wine/save-wine')">
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
							{{seat[index]?seat[index].name:"您尚未选座？选座"}}
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
					<a :href="item.url" target="_blank">
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
				seat: [], // 选座（或选包间）
				banners: [], // 顶部广告
				news: [], // 底部新鲜趣事
			}
		},
		components: {
			uniCard,
		},
		created() {
			this.initHomeData()
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			setTimeout(this.$checkLogin("/pages/index/index", "2"), 5000)
		},
		methods: {
			// 初始化首页数据
			initHomeData() {
				this.$api.home.reqHomeData().then(({
					code,
					data
				}) => {
					const {
						banners,
						seat,
						news
					} = data
					code === 200 && ([this.banners, this.seat, this.news] = [banners, seat, news])
				})
			},
			// 监听顾客选择座位的回调
			bindPickerChange(e) {
				var _this = this
				_this.index = e.detail.value;
				_this.index !== 0 && setTimeout(function() {
					_this.$api.home.reqSetSeat({
						username: 123456,
						..._this.seat[_this.index]
					}).then(({
						code,
						data
					}) => {
						code === 200 && uni.showToast({
							title: data.msg,
							icon: 'success'
						});
					})
				}, 1000)
			},
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
				overflow: hidden;

				a>img {
					width: 100%;
					height: 100%;
					border-radius: 20rpx;
					max-height: 215px;
				}
			}
		}
	}

	@media screen and(min-width:540px) {
		.news>h4 {
			font-size: 24px
		}
	}
</style>
