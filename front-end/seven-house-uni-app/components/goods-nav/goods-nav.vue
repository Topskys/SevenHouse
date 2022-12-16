<template>
	<view class="goods__nav r-flex-3">
		<view class="r-flex-3" style="width: 100%;">
			<view class="left r-flex-5">
				<uni-badge class="uni-badge-left-margin" :text="order.count" absolute="rightTop" size="small">
					<view class="fa-shopping-bag" @click="showPop">
						<img src="/static/images/shopping-bag.png" style="width:60rpx;height: 60rpx;">
					</view>
				</uni-badge>
				<!-- #e31d1a -->
				<text
					style="margin-left:20rpx;font-size: 28rpx;color: #dd524d;">{{order.total?order.total.toFixed(2):''}}</text>
			</view>
			<view class="right">
				<!-- <button type='default' size='mini' style="color:#fff;background-color: #42b983 ;margin-right: 20rpx;"
					@click='updateOrder'>加入购物车</button> -->
				<button type='primary' size='mini' @click='navPayOrder'
					:disabled="openStore=='0'?false:true">{{openStore=='0'?'立即购买':'休息中'}}</button>
			</view>
		</view>
		<pop ref="pop" direction="below" :is_close="true" :is_mask="true" :width="100" height="60vh" :maskFun="true">
			<scroll-view scroll-y="true" class='right__scroll'>
				<view class="good__item r-flex-1" v-for="item,i in shopCart" :key='i'>
					<view class="good__img">
						<img :src="item.imgUrl" class="img" />
					</view>
					<view class="good__desc ">
						<p class='good__name'>{{item.name}}</p>
						<p class='good__intro'>规格：{{item.alcoholsSkuList[item.skuId-1].skuCap}}</p>
						<view class="r-flex-3" style="margin-top: 10rpx;">
							<view class="left">
								<text style="color: #ff7250;font-size: 26rpx;">￥</text><text
									style="color: #ff7250;font-size: 32rpx;font-weight: bold;">{{item.alcoholsSkuList[item.skuId-1].skuSellprice}}</text>
							</view>
							<view class="popup-count r-flex-1">
								<view class="count-left count" v-show="item.num>0?true:false">
									<img src="/static/images/reduce.png"
										@click="addToCart(item.alcoId,item.skuId,'sub')" class='count-img'>
								</view>
								<view class="count-middle count" style="color: #000;">
									{{item.num>99?'99+':item.num}}
								</view>
								<view class="count-right count">
									<img src="/static/images/add.png" @click="addToCart(item.alcoId,item.skuId,'add')"
										class='count-img'>
								</view>
							</view>
						</view>

					</view>
				</view>
			</scroll-view>
		</pop>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	import uniBadge from '@/components/uni-badge_1.2.1/components/uni-badge/uni-badge.vue'
	import pop from '@/common/ming-pop_1.0.4/components/ming-pop/ming-pop.vue'
	export default {
		props: ['openStore'],
		components: {
			uniBadge,
			pop,
		},
		computed: {
			// ...mapState('m_order', ['shopCart', 'order']) // 微信不起作用
			...mapState({
				shopCart: (state) => state.m_order.order.shopCart,
				order: (state) => state.m_order.order,
			})
		},
		methods: {
			...mapMutations('m_order', ['updateSpread', 'addCart']),
			// 加入购物车按钮的回调
			// 导航到支付订单页面
			navPayOrder: function() {
				this.shopCart.length > 0 ? this.$api.order.reqUpdateOrder({
					...this.$store.state.m_order.order,
				}).then(({
					code,
					data,
					msg
				}) => {
					uni.showToast({
						icon: code === 200 ? 'success' : 'error'
					})
					code == 200 && uni.navigateTo({
						url: `/pages/pay/pay?order=${encodeURIComponent(JSON.stringify({...this.$store.state.m_order.order,orderId:data}))}`,
					})
				}) : uni.showToast({
					icon: 'error',
					title: "请先选择商品！"
				})
			},
			shopCartHandler() {
				this.updateSpread()
			},
			showPop() {
				this.$refs.pop.show();
			},
			// pop完成按钮的回调
			closePop() {
				this.$refs.pop.close();
			},
			// + or -
			addToCart(alcoId, skuId, which) {
				this.addCart({
					alcoId,
					skuId,
					which,
				});
			},
		},
	}
</script>

<style lang="scss" scoped>
	.goods__nav {
		box-sizing: border-box;
		padding: 10rpx 30rpx;
		background-color: #333;
		width: 100vw;
		color: #ffF;
	}

	.shopping-cart {
		position: fixed;
		// bottom: 62.78px;
		bottom: 0;
		left: 0;
		background-color: rgb(247, 247, 250);
		backdrop-filter: none;
		width: 100vw;
		height: 40vh;
		z-index: 999;
	}

	.right__scroll {
		margin-top: 15px;
		max-height: calc(60vh - 170rpx);

		.good__item {
			padding: 20rpx 30rpx;

			.good__img {
				margin-right: 20rpx;
				width: 150rpx;
				height: 150rpx;

				.img {
					width: 100%;
					height: 100%;
					border-radius: 10rpx;
				}
			}

			.good__desc {
				flex: 1;

				.good__name {
					color: #333;
					font-size: 30rpx;
				}

				.good__intro {
					color: #777;
					font-size: 24rpx;
					margin-top: 10rpx;
				}

				.popup-count {
					.count {
						width: 48rpx;
						height: 48rpx;

						.count-img {
							width: 100%;
							height: 100%;
						}
					}

					.count-middle {
						width: auto;
						margin: 0 20rpx;
					}

				}
			}
		}
	}
</style>
