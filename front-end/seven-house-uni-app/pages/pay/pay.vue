<template>
	<view class="pay__container">
		<!-- 商品列表 -->
		<scroll-view scroll-y="true" class="scroll">
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in pay.orders" :key='item.id'>
				<view class="order__item">
					<view class="order__body r-flex-1 ">
						<view class="body__left" style="margin-right: 10px;">
							<img :src="item.imgUrl" style='width: 75px; height: 75px; border-radius: 5px;' />
						</view>
						<view class="body__right" style="flex: 1;">
							<b style='color: #000;font-size: 16px;'>{{item.name}}</b>
							<view style="font-size: 14px;">
								<span>数量：</span> <span style='color: orangered;'>x{{item.count}}</span>
							</view>
							<view style="font-size: 14px;">
								<span>单价：</span> <span style='color: orangered;'>￥{{item.price}}</span>
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
				<span>总金额</span><span style='color:#42b983;'>￥{{pay.totalPrice}}</span>
			</view>
			<view class="r-flex-3 li">
				<span>优惠</span><span style='color:#42b983;'>￥0</span>
			</view>
			<view class="r-flex-3 li">
				<span>数量</span><span>{{pay.totalCount}}</span>
			</view>
		</view>
		<!-- 支付配置 -->
		<view class="pay__config">
			<view class="container">
				<view class="ui-all">
					<view class="ui-list right">
						<text>是否存酒</text>
						<picker @change="bindPickerChange1" mode='selector' range-key="name" :value="index1"
							:range="saveWine">
							<view class="picker">
								{{saveWine[index1].name}}
							</view>
						</picker>
					</view>
					<view class="ui-list right">
						<text>支付方式</text>
						<picker @change="bindPickerChange2" mode='selector' range-key="name" :value="index2"
							:range="payWay">
							<view class="picker">
								{{payWay[index2].name}}
							</view>
						</picker>
					</view>
					<view class="ui-list">
						<text>备注</text>
						<input type="text" :placeholder="value" :value="pay.note" @input="bindnickNote"
							placeholder-class="place" />
					</view>
				</view>

			</view>
		</view>
		<!-- 支付控制栏 -->
		<view class="pay__bar r-flex-3">
			<view class="pay__left">
				实付<text class="pay__total"><span class='pay__cn'>￥</span><span>{{pay.totalPrice}}</span></text>
			</view>
			<view class="pay__right">
				<button type="primary" size="mini" @click="payOrder">支付订单</button>
			</view>
		</view>
	</view>
</template>

<script>
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	export default {
		components: {
			uniCard,
		},
		data() {
			return {
				value: '请填写',
				index1: 0,
				index2: 0,
				saveWine: [{
					id: 1,
					name: '否'
				}, {
					id: 2,
					name: '是'
				}],
				payWay: [{
					id: 1,
					name: '支付宝'
				}, {
					id: 2,
					name: '微信'
				}],
				pay: {
					orders: [],
					note: '',
					totalPrice: 0,
					totalCount: 0,
				}
			}

		},
		onLoad: function(option) {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			setTimeout(this.$checkLogin("/pages/pay/pay", "1"), 5000)
			// 获取参数
			const orders = JSON.parse(decodeURIComponent(option.orders));
			// (orders || orders.length) && this.dealOrder(orders)
			if (orders || orders.length) {
				this.pay.orders = orders.list
				this.pay.totalCount = orders.count
				this.pay.totalPrice = orders.total
			}
		},
		methods: {
			// 订单数据预处理
			// dealOrder(arr = []) {
			// 	[this.pay.totalPrice, this.pay.totalCount] = [`${arr.reduce((sum, item) => sum += item.payPrice, 0)}`,
			// 		`${arr.reduce((sum, item) => sum += item.count, 0)}`
			// 	]
			// 	this.pay.orders = arr
			// },
			// 支付订单按钮的回调
			payOrder: function() {
				this.$api.order.reqPayOrder(this.pay).then(({
					code,
					data
				}) => {
					code === 200 ? (data.payToken && [
						uni.showToast({
							title: data.msg || '支付成功',
							icon: 'success',
							duration: 2000,
						}),
						setTimeout(() => {
							uni.switchTab({
								url: `/pages/order/order`
							})
						}, 2500)
					]) : uni.showToast({
						title: data.msg || '支付失败',
						icon: 'error'
					})
				})
			},
			bindPickerChange1(e) {
				this.index1 = e.detail.value;
				this.pay.saveWine = this.saveWine[this.index1].name
			},
			bindPickerChange2(e) {
				this.index2 = e.detail.value;
				this.pay.payWay = this.payWay[this.index2].name
			},
			bindnickNote(e) {
				this.pay.note = e.detail.value;
			},

		},


	}
</script>


}
</script>

<style lang="scss" scoped>
	.pay__container {
		height: calc(100vh - 44px);
		background-color: #eee;
		position: relative;
		overflow: hidden;

		.scroll {
			max-height: 360rpx;
			padding: 18rpx 0;
			margin: 5px 0;
			background-color: #fff;
			box-sizing: border-box;
		}

		.computed__price {
			padding: 18rpx 38rpx;
			margin: 5px 0;
			background-color: #fff;

			.li {
				padding: 18rpx 0;
				color: #4a4a4a;
				font-size: 13px;
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
			padding: 10px 30rpx;
			background-color: #fff;

			.pay__left {
				font-size: 13px;
				color: #4a4a4a;

				.pay__total {
					color: orange;
					font-size: 16px;

					.pay__cn {
						font-size: 12px;
					}
				}
			}


		}
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
