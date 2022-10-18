<template>
	<scroll-view scroll-y="true" class="scroll">
		<uni-card :isShadow="false" class="customer__info" style="margin-top: 100px;">
			<view class="  r-flex-3">
				<view class="info__left r-flex-3">
					<view class="customer__avatar">
						<img
							:src="userInfo?userInfo.avatarUrl:'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'">
					</view>
					<view>
						<b @click='userInfo?"":navPage("/pages/login/login")'>{{userInfo?userInfo.nickname:'登录'}}</b>
						<p>{{userInfo?`ID:${userInfo.id}`:'ID:0000001'}}</p>
					</view>
				</view>
				<view class="info__right">{{userInfo?`${userInfo.class}`:'LV1'}}</view>
			</view>
			<!-- <view style="margin-top: 10px;">
				<p>会员</p>
				<progress :percent="last" show-info active />
			</view> -->
		</uni-card>
		<uni-card :isShadow="false" style='border-radius: 20rpx;'>
			<view class="score__discount">
				<view class="customer__score">
					<h4>我的积分</h4>
					<b>{{userInfo?userInfo.score:0}}</b>
					<p>快来用积分兑换您喜欢的宝贝吧~</p>
				</view>
				<view class="customer__discount">
					<h4>优惠劵</h4>
					<b>{{userInfo?userInfo.discount:0}}</b>
					<p>快来使用优惠劵吧~</p>
				</view>
			</view>
		</uni-card>
		<!-- 我的服务 -->
		<uni-card :isShadow="false" class="myServer">
			<h4>我的服务</h4>
			<view class="server__list">
				<view class="server__item c-flex-2" v-for="item,index in server" :key="item.title"
					@click="navPage(item.path)">
					<img :src="item.icon" />
					<span>{{item.title}}</span>
				</view>
			</view>
		</uni-card>
	</scroll-view>
</template>

<script>
	import {
		mapGetters,
		mapMutations,
		mapState,
	} from 'vuex'
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	export default {
		data() {
			return {
				last: 45, // 存酒余量
				server: [{
						icon: "static/images/club.png",
						title: '官方部落',
						path: "/pages/club/club",
					},
					{
						icon: "static/images/database.png",
						title: '我的存酒',
						path: "/pages/save-wine/save-wine",
					},
					{
						icon: "static/images/info.png",
						title: '个人信息',
						path: "/pages/selfInfo/selfInfo",
					},
					{
						icon: "static/images/discount.png",
						title: '优惠劵',
						path: "/pages/discount/discount",
					},
					{
						icon: "static/images/more.png",
						title: '更多服务',
						path: "",
					}
				],
			}
		},
		components: {
			uniCard,
		},
		computed: {
			...mapState({
				userInfo: (state) => {
					let userInfo = state.m_user.userInfo
					if (userInfo.avatarUrl) {
						return userInfo
					} else {
						return ""
					}
				}
			})
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			setTimeout(this.$checkLogin("/pages/mine/mine", "2"), 5000)
		},
		methods: {
			// 页面跳转，uni.redirectTo()：只能打开非tabBar页面的路径
			navPage: (path = '') => uni.navigateTo({
				url: path ? path : uni.showToast({
					title: '开发中',
					icon: 'none'
				})
			}),
		}
	}
</script>

<style lang="scss" scoped>
	.scroll {
		min-height: calc(100vh - 44px - 62.78px);
		background-image: linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%);
		overflow: hidden;

		.customer__info {
			border-radius: 20rpx;

			.info__left {
				.customer__avatar {
					width: 50px;
					height: 50px;
					margin-right: 10px;

					img {
						border-radius: 50%;
						width: 100%;
						height: 100%;
					}
				}

				b {
					color: #000;
					font-size: 18px;
				}

				p {
					opacity: .8;
					font-size: 14px;
				}
			}

			.info__right {
				color: darkgoldenrod;
				font-size: 18px;
			}
		}

		.score__discount {
			display: grid;
			grid-template-columns: repeat(2, 1fr);
			gap: 20px;

			h4 {
				color: #000;
			}

			b {
				color: #000;
				font-size: 16px;
				line-height: 30px;
			}

			p {
				opacity: .8;
				font-size: 12px;
			}
		}

		.myServer {
			border-radius: 20rpx;

			h4 {
				color: #000;
			}

			.server__list {
				margin: 20rpx 0;
				display: grid;
				grid-template-columns: repeat(4, 1fr);
				gap: 40rpx;

				.server__item {
					img {
						width: 30px;
						height: 30px;
					}

					span {
						font-size: 14px;
						margin-top: 10rpx;
					}
				}
			}
		}
	}
</style>
