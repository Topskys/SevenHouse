<template>
	<view class="obj__container">
		<!-- 商品列表 -->
		<scroll-view scroll-y="true" class="scroll">
			<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in order.orderItemList"
				:key='item.alcoId'>
				<view class="order__item">
					<view class="order__body r-flex-1 ">
						<view class="body__left" style="margin-right: 20rpx;">
							<img :src="item.alcoImage" style='width: 150rpx; height: 150rpx; border-radius: 10rpx;' />
						</view>
						<view class="body__right" style="flex: 1;">
							<b style='color: #000;font-size: 32rpx;'>{{item.alcoName}}</b>
							<view style="font-size: 28rpx;">
								<span>数量：</span> <span>x{{item.buyNum}}</span>
							</view>
							<view style="font-size: 28rpx;">
								<span>单价：</span> <span style='color: orangered;'>￥{{item.acloPrice}}</span>
							</view>
						</view>
					</view>
				</view>
			</uni-card>
		</scroll-view>
		<view class="computed__price">
			<view class="li">订单编号：{{order.id}}</view>
			<view class="li">数量：{{order.count|| order.orderItemList.length}} </view>
			<view class="li">
				<span>优惠：</span><span style='color:#42b983;'>￥0</span>
			</view>
			<view class=" li">
				<span>总金额：</span><span style='color: orangered;'>￥{{order.total}}</span>
			</view>
			<view class="li">订单状况：{{order.status}}</view>
			<view class="li">创建时间：{{order.createTime}}</view>
		</view>
		<!-- 支付配置 -->
		<view class="pay__config">
			<view class="container">
				<view class="ui-all">
					<!-- <view class="ui-list ">
						<text>是否存酒：{{order.isDeposit}}</text>
					</view> -->
					<view class="ui-list ">
						<text>支付方式：{{order.payType}}</text>
					</view>
					<view class="ui-list">
						<text>备注：{{order.remark}}</text>
					</view>
				</view>
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
				order: {}
			}

		},
		onLoad: function(option) {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			this.$checkLogin("/pages/order-detail/order-detail", "2");
			// 获取参数
			this.order = JSON.parse(decodeURIComponent(option.order));
			// console.log(this.order)
		},
	}
</script>


}
</script>

<style lang="scss" scoped>
	.obj__container {
		// height: calc(100vh - 44px);// h5
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
				font-size: 26rpx;
				color: #4a4a4a;

				.pay__total {
					color: orange;
					font-size: 32rpx;

					.pay__cn {
						font-size: 24rpx;
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
