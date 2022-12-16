<template>
	<scroll-view scroll-y="true" class="scroll">
		<view class="customer__before"></view>
		<uni-card :isShadow="false" class="customer__info">
			<view class="  r-flex-3">
				<view class="info__left r-flex-3">
					<view class="customer__avatar">
						<img
							:src="userInfo.avatarUrl?userInfo.avatarUrl:'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'">
					</view>
					<view>
						<b @click='userInfo?"":navPage("/pages/login/login")'
							class="nickname">{{userInfo.nickname?userInfo.nickname:'登录'}}</b>
						<p>{{userInfo.id?`ID:${userInfo.id}`:'ID:0000001'}}</p>
					</view>
				</view>
				<view class="info__right">{{userInfo.class?`${userInfo.class}`:'LV1'}}</view>
			</view>
		</uni-card>
		<uni-card :isShadow="false" style='border-radius: 20rpx;'>
			<view class="score__discount">
				<view class="customer__score">
					<h4>我的余额</h4>
					<b>{{userInfo.integral?'￥'+userInfo.integral:0}}</b>
					<p>充值500元送<span style="color: orangered;">100</span>元</p>
				</view>
				<view class="customer__discount">
					<h4>优惠劵</h4>
					<b>{{userInfo.discount?userInfo.discount:0}}</b>
					<p>快来使用优惠劵吧~</p>
				</view>
			</view>
		</uni-card>
		<!-- 我的服务 -->
		<uni-card :isShadow="false" class="myServer">
			<h4>我的服务</h4>
			<view class="server__list">
				<view class="server__item c-flex-2" @click="showPop">
					<img src="/static/images/wallet.png" />
					<span>充值</span>
				</view>
				<view class="server__item c-flex-2" v-for="item,index in server" :key="item.title"
					@click="navPage(item.path)">
					<img :src="item.icon" />
					<span>{{item.title}}</span>
				</view>
			</view>
		</uni-card>
		<!-- 充值弹窗 -->
		<pop ref="pop" direction="center" :is_close="true" :is_mask="true" :width="80" height="30vh" :maskFun="true">
			<view class="popup-take">
				<view class="popup-head">充值金额</view>
				<input v-model="upMoney" type="number" focus placeholder="输入充值金额" />
				<!-- <view class="pop-btns"> -->
				<!-- <button @click='cancel' size='mini' class="popup-button">取消</button> -->
				<button type="primary" @click='payIn' size='mini' class="popup-button">确定</button>
				<!-- </view> -->
			</view>
		</pop>
	</scroll-view>
</template>

<script>
	import {
		mapGetters,
		mapMutations,
		mapState,
	} from 'vuex'
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	import pop from '@/common/ming-pop_1.0.4/components/ming-pop/ming-pop.vue'
	export default {
		data() {
			return {
				last: 45, // 存酒余量
				upMoney: '', // 充值金额
				server: [{
						icon: "/static/images/club.png",
						title: '官方部落',
						path: "/pages/club/club",
					},
					{
						icon: "/static/images/database.png",
						title: '我的存酒',
						path: "/pages/save-wine/save-wine",
					},
					{
						icon: "/static/images/info.png",
						title: '个人信息',
						path: "/pages/selfInfo/selfInfo",
					},
					{
						icon: "/static/images/discount.png",
						title: '优惠劵',
						path: "/pages/discount/discount",
					},
					{
						icon: "/static/images/more.png",
						title: '更多服务',
						path: "",
					}
				],
			}
		},
		components: {
			uniCard,
			pop,
		},
		computed: {
			...mapState({
				userInfo: (state) => {
					let userInfo = state.m_user.userInfo
					if (userInfo.id) {
						return userInfo
					} else {
						return ""
					}
				}
			})
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			this.$checkLogin("/pages/mine/mine", "2");
		},
		methods: {
			...mapMutations('m_user', ['updateUserInfo', 'deleteUserInfo']),
			// 页面跳转，uni.redirectTo()：只能打开非tabBar页面的路径
			navPage: (path = '') => uni.navigateTo({
				url: path ? path : uni.showToast({
					title: '开发中',
					icon: 'none'
				})
			}),
			// 充值按钮的回调
			payIn() {
				this.$api.user.reqPayIn({
					integral: this.upMoney
				}).then(({
					code,
					data,
					msg
				}) => {
					if (code === 200) {
						this.$api.user.reqUserInfo(this.userInfo.id).then(({
							code,
							data,
							// msg
						}) => {
							// 更新用户信息
							code === 200 && this.updateUserInfo(data)
							uni.showToast({
								title: msg,
								icon: code === 200 ? 'success' : 'error'
							})
							console.log(data)
						})
					} else {
						uni.showToast({
							title: msg,
							icon: 'error'
						})
					}
					// uni.showToast({
					// 	title: msg,
					// 	icon: code === 200 ? 'success' : 'error'
					// })
					this.upMoney = '';
					this.$refs.pop.close();
				})
			},
			showPop() {
				this.$refs.pop.show();
			},
			cancel() {
				this.$refs.pop.close();
			}
		}
	}
</script>

<style lang="scss" scoped>
	.scroll {
		height: 100vh;
		background-image: linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%);
		overflow: hidden;

		.customer__before {
			width: 100%;
			height: 200rpx;
			background: transparent;
		}

		.customer__info {
			border-radius: 20rpx;

			.info__left {
				.customer__avatar {
					width: 120rpx;
					height: 120rpx;
					margin-right: 20rpx;

					img {
						border-radius: 50%;
						width: 100%;
						height: 100%;
					}
				}

				.nickname {
					color: #000;
					font-size: 36rpx;
				}

				p {
					opacity: .8;
					font-size: 28rpx;
				}
			}

			.info__right {
				color: darkgoldenrod;
				font-size: 36rpx;
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
				font-size: 32rpx;
				line-height: 60rpx;
			}

			p {
				opacity: .8;
				font-size: 24rpx;
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
						width: 60rpx;
						height: 60rpx;
					}

					span {
						font-size: 28rpx;
						margin-top: 10rpx;
					}
				}
			}
		}
	}

	.popup-take {
		position: relative;
		width: 100%;
		height: 100%;

		.popup-head {
			margin: 20rpx 0 50rpx 0;
		}

		input {
			border: 1px solid #eee;
			padding: 10rpx 20rpx;
		}

		.popup-button {
			position: absolute;
			bottom: 20rpx;
			width: 100%;
			letter-spacing: 20rpx;
		}
	}
</style>
