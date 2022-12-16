<template>
	<view class="pay__container">
		<!-- 商品列表 -->
		<scroll-view scroll-y="true" class="scroll">
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in pay.shopCart || pay.orderItemList"
				:key='item.alcoId'>
				<view class="order__item">
					<view class="order__body r-flex-1 ">
						<view class="body__left" style="margin-right: 20rpx;">
							<img :src="item.imgUrl||item.alcoImage"
								style='width: 150rpx; height: 150rpx; border-radius: 10rpx;' />
						</view>
						<view class="body__right" style="flex: 1;">
							<b style='color: #000;font-size: 32rpx;'>{{item.name||item.alcoName}}</b>
							<view>
								<view class="r-l">
									<span>数量：</span> <span style='color: orangered;'>x{{ item.num||item.buyNum}}</span>
								</view>
								<view class="r-l r-flex-3">
									<span>规格：{{item.alcoSku||item.alcoholsSkuList[item.skuId-1].skuCap}}</span>
									<view class="r-l">
										<span>单价：</span> <span
											style='color: orangered;'>￥{{item.acloPrice||item.alcoholsSkuList[item.skuId-1].skuSellprice}}</span>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</uni-card>
		</scroll-view>
		<!-- 计算价格 -->
		<view class="computed__price">
			<view class="r-flex-3 li">
				<span>优惠劵</span><span>暂无可用优惠劵</span>
			</view>
			<view class="r-flex-3 li">
				<span>总金额</span><span style='color:#42b983;'>￥{{pay.total}}</span>
			</view>
			<view class="r-flex-3 li">
				<span>优惠</span><span style='color:#42b983;'>￥0</span>
			</view>
			<view class="r-flex-3 li">
				<span>数量</span><span>{{pay.count}}</span>
			</view>
		</view>
		<!-- 支付配置 -->
		<view class="pay__config">
			<view class="container">
				<view class="ui-all">
					<!-- <view class="ui-list right" v-show="pay.total>120?pay.sendType?false:true:false">
						<text>选座</text>
						<picker @change="bindPickerChange" mode='selector' range-key="name" :value="index"
							:range="seat">
							<view class="picker">
								{{seat[index]?seat[index].name:"您尚未选座？选座"}}
							</view>
						</picker>
					</view> -->
					<!-- <view class="ui-list right">
						<text>送餐方式</text>
						<picker @change="bindPickerChange1" mode='selector' range-key="name" :value="index1"
							:range="sendType">
							<view class="picker">
								{{sendType[index1].name}}
							</view>
						</picker>
					</view> -->
					<view class="ui-list">
						<radio-group @change="radioChange">
							<label class="radios" v-for="(item, index) in sendType" :key="item.name">
								<view>
									<radio class="radio" :value="item.name" :checked="index === index1"
										color='rgb(19, 206, 102)' />
								</view>
								<view>{{item.name}}</view>
							</label>
						</radio-group>
					</view>
					<!-- pay.sendType=='送餐到桌'?true:false -->
					<view class="ui-list right" v-show="showSeat">
						<text>选座</text>
						<picker @change="bindPickerChange" mode='selector' range-key="name" :value="index"
							:range="seat">
							<view class="picker">
								{{seat[index]?seat[index].name:"您尚未选座？选座"}}
							</view>
						</picker>
					</view>
					<view class="ui-list right">
						<text>支付方式</text>
						<picker @change="bindPickerChange2" mode='selector' range-key="name" :value="index2"
							:range="payType">
							<view class="picker">
								{{payType[index2].name}}
							</view>
						</picker>
					</view>
					<view class="ui-list">
						<text>备注</text>
						<input type="text" :placeholder="value" :value="pay.remark" @input="bindnickNote"
							placeholder-class="place" />
					</view>
				</view>

			</view>
		</view>
		<!-- 支付控制栏 -->
		<view class="pay__bar r-flex-3">
			<view class="pay__left">
				<!-- 实付<text class="pay__total"><span class='pay__cn'>￥</span><span class='total'>{{pay.total}}</span></text> -->
				实付<span class='pay__cn'>￥</span><span class='total'>{{pay.total}}</span>
			</view>
			<view class="pay__right">
				<button type="primary" size="mini" @click="payOrder"
					:disabled="openStore=='0'?false:true">{{openStore=='0'?'支付订单':'休息中'}}</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapGetters,
		mapMutations,
		mapState,
	} from 'vuex'
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
import { getFreeDesk } from '../../api/order';
	export default {
		components: {
			uniCard,
		},
		data() {
			return {
				value: '请填写',
				index1: -1,
				index2: 0,
				sendType: [{
					id: 1,
					name: '送餐到桌'
				}, {
					id: 2,
					name: '吧台取酒'
				},
				{
					id: 3,
					name: '开桌'
				},
				],
				payType: [{
					id: 1,
					name: '余额'
				}, {
					id: 2,
					name: '支付宝'
				}, {
					id: 3,
					name: '微信'
				}],
				pay: {},
				index: 0,
				seat: [], // 选座（或选包间）
				openStore: true, // 店铺是否营业,
				showSeat:false,// 控制选座显示或隐藏
			}

		},
		onLoad: function(option) {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			this.$checkLogin("/pages/pay/pay", "1");
			// 获取参数
			this.pay = JSON.parse(decodeURIComponent(option.order));
			this.pay.deskId = 1;
			this.pay.payType = '余额';
			this.$set(this.pay, "sendType", null);
			// console.log("pay：", this.pay)
		},
		computed: {
			...mapState({
				userInfo: function(state) {
					let userInfo = state.m_user.userInfo
					if (userInfo.id) {
						return userInfo
					} else {
						this.reLogin()
						return ""
					}
				}
			}),
		},
		onShow() {
			this.init();
			this.isWorking();
		},
		methods: {
			...mapMutations('m_user', ['updateUserInfo', 'deleteUserInfo', 'deleteToken']),
			...mapMutations('m_order', ['deleteOrder']),
			// 支付订单按钮的回调
			payOrder: function() {
				this.$api.order.reqPayOrder({
					orderDTO: {
						id: this.pay.orderId || this.pay.id,
						remark: this.pay.remark,
						payType: this.pay.payType || '余额',
						total: this.pay.total,
						deskId: this.pay.deskId || "未选座",
						sendType: this.pay.sendType || "",
					}
				}).then(({
					code,
					data,
					msg
				}) => {
					// code === 200 ? ([
					// 	uni.showToast({
					// 		title: msg || '支付成功',
					// 		icon: 'success',
					// 		duration: 2000,
					// 	}),
					// 	setTimeout(() => {
					// 		uni.switchTab({
					// 			url: `/pages/order/order`
					// 		})
					// 	}, 2500)
					// ]) : uni.showToast({
					// 	title: msg || '支付失败',
					// 	icon: 'error'
					// })
					uni.showToast({
						title: msg,
						icon: code===200?'success':'error',
						duration: 2000,
					})
					if (code === 200) {
						setTimeout(() => {
							uni.switchTab({
								url: `/pages/order/order`
							})
						}, 2500);
						this.deleteOrder(); // 清空购物车
						this.$api.user.reqUserInfo(this.userInfo.id).then(({
							code,
							data,
							msg
						}) => {
							// 更新用户信息
							code === 200 && this.updateUserInfo(data)
							uni.showToast({
								// title: msg,
								icon: code === 200 ? 'success' : 'error'
							})
						})
					}
				})
			},
			// 单选框的回调
			radioChange({
				detail
			}) {
				this.pay.sendType = detail.value
				this.showSeat=detail.value!=='吧台取酒'?true:false;
				if(detail.value=="开桌"){
					this.$api.order.getFreeDesk().then(({	
						code,
						data
					}) => {
						
						code === 200 && [data.map(item => item.name = item.deskNo), this.seat = data]
					})
				}else if(detail.value=="送餐到桌"){
					this.init();
				}
				
			},
			// bindPickerChange1(e) {
			// 	this.index1 = e.detail.value;
			// 	this.pay.sendType = this.sendType[this.index1].name
			// },
			bindPickerChange2(e) {
				this.index2 = e.detail.value;
				this.pay.payType = this.payType[this.index2].name
			},
			bindnickNote(e) {
				this.pay.remark = e.detail.value;
			},
			// 初始化页面数据
			init() {
				this.$api.home.reqHomeData().then(({
					code,
					data
				}) => {
					let {
						list
					} = data
					code === 200 && [list.map(item => item.name = item.deskNo), this.seat = list]
				})
			},
			// 监听顾客选择座位的回调
			bindPickerChange(e) {
				var _this = this
				this.index = e.detail.value;
				this.pay.deskId = this.seat[this.index].id
				// _this.index !== 0 && _this.$api.home.reqSetSeat({
				// 	..._this.seat[_this.index]
				// }).then(({
				// 	code,
				// 	data,
				// 	msg
				// }) => {
				// 	_this.init()
				// 	uni.showToast({
				// 		title: msg,
				// 		icon: code === 200 ? 'success' : 'error'
				// 	})
				// })
			},
			reLogin() {
				this.deleteUserInfo("userInfo")
				this.deleteToken("token")
				uni.navigateTo({
					url: "/pages/login/login",
				})
			},
			// 检查是否开业
			isWorking() {
				this.$api.order.reqIsOpenStore().then(({
					code,
					data,
					msg
				}) => {
					code === 200 ? (this.openStore = data.status) : uni.showToast({
						title: 'No Working',
						icon: 'error'
					});
				});
			},
		},


	}
</script>


}
</script>

<style lang="scss" scoped>
	.pay__container {
		// height: calc(100vh - 44px);
		height: 100vh;
		background-color: #eee;
		position: relative;
		overflow: hidden;

		.scroll {
			max-height: 360rpx;
			padding: 18rpx 0;
			margin: 10rpx 0;
			background-color: #fff;
			box-sizing: border-box;
		}

		.computed__price {
			padding: 18rpx 38rpx;
			margin: 10rpx 0;
			background-color: #fff;

			.li {
				padding: 18rpx 0;
				color: #4a4a4a;
				font-size: 26rpx;
				border-bottom: solid 1px #f2f2f2;
			}
		}

		.pay__config {
			background-color: #fff;
		}

		.pay__bar {
			position: absolute;
			bottom: 0;
			left: 0;
			width: 100vw;
			box-sizing: border-box;
			padding: 20rpx 30rpx;
			background-color: #fff;

			.pay__left {
				font-size: 28rpx;
				color: #4a4a4a;

				.pay__cn {
					font-size: 24rpx;
					margin-left: 10rpx;
				}

				.total {
					color: #ff4500;
					font-size: 32rpx;
					font-weight: bold;
				}
			}


		}
	}

	.body__right {
		.r-l {
			font-size: 20rpx;
		}
	}

	.radios {
		display: flex;
		align-items: center;
		font-size: 30rpx;
		color: #030303;

		.radio {
			margin: 10rpx 50rpx 10rpx 0;
		}
	}

	::v-deep .uni-radio-input {
		width: 40rpx;
		height: 40rpx;
	}
</style>


<style lang="scss" scoped>
	.container {
		display: block;
	}

	.ui-all {
		padding: 20rpx 40rpx;

		.ui-list {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			text {
				color: #4a4a4a;
				font-size: 28rpx;
				display: inline-block;
				vertical-align: middle;
				min-width: 150rpx;
			}

			input {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
			}

			button {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				background: none;
				margin: 0;
				padding: 0;

				&::after {
					display: none;
				}
			}

			picker {
				width: 90%;
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				position: absolute;
				top: 30rpx;
				left: 150rpx;
			}

			textarea {
				color: #030303;
				font-size: 30rpx;
				vertical-align: middle;
				height: 150rpx;
				width: 100%;
				margin-top: 50rpx;
			}

			.place {
				color: #999999;
				font-size: 28rpx;
			}
		}

		.right:after {
			content: ' ';
			width: 20rpx;
			height: 20rpx;
			border-top: solid 1px #030303;
			border-right: solid 1px #030303;
			transform: rotate(45deg);
			-ms-transform: rotate(45deg);
			/* IE 9 */
			-moz-transform: rotate(45deg);
			/* Firefox */
			-webkit-transform: rotate(45deg);
			/* Safari 和 Chrome */
			-o-transform: rotate(45deg);
			position: absolute;
			top: 40rpx;
			right: 0;
		}

		.save {
			background: #030303;
			border: none;
			color: #ffffff;
			margin-top: 40rpx;
			font-size: 28rpx;
		}
	}
</style>
