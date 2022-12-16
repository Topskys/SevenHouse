<template>
	<view class="classify__container">
		<!-- 小程序不能用 -->
		<!-- <marquee vspace='20' hspace='30' width='100%' bgcolor="#ff7250" height='60rpx'
			style="line-height: 60rpx;color:#eee;">
			{{notice}}
		</marquee> -->
		<view class="classify__body r-flex-1">
			<scroll-view scroll-y="true" class='left__scroll'>
				<view v-for="item,index in categoryList" :key='item.id' @click='clickLeft(item)'
					:class='(index+1)==current?"left__active":""' class="menu__item">
					{{item.name}}
				</view>
			</scroll-view>
			<scroll-view scroll-y="true" class='right__scroll'>
				<view class="good__item r-flex-1" v-for="item in goodList" :key='item.alcoId'>
					<view class="good__img">
						<img :src="item.imgUrl" />
					</view>
					<view class="good__desc ">
						<p class='good__name'>{{item.name}}</p>
						<p class='good__intro'>{{item.alcoDec}}</p>
						<view class=" r-flex-3" style="margin-top: 10rpx;">
							<view class="left">
								<text style="color: #ff7250;font-size: 26rpx;">￥</text><text
									style="color: #ff7250;font-size: 32rpx;font-weight: bold;">{{item.alcoOriginprice}}</text>
							</view>
							<view class="rank__button" @click="showPop(item)">选规格</view>
							<!-- 						<wmNumberBox destination="body-btn" model="2" @change="numberBoxClick($event,item)">
							</wmNumberBox> -->
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
		<!-- 购物导航栏 -->
		<goods-nav id="body-btn" :openStore="openStore"></goods-nav>
		<!-- 选规格 -->
		<pop ref="pop" direction="below" :is_close="true" :is_mask="true" :width="100" height="70vh" :maskFun="true">
			<view class="good-popup">
				<scroll-view scroll-y="true" style="max-height: 50vh;">
					<view>
						<!-- <img src="/static/images/girl.jpg" class="popup-img"> -->
						<image :src="goods.imgUrl" mode="aspectFill" class="popup-img"></image>
						<view style="font-size: 36rpx;color: black;">{{goods.name|| '该商品正在上架中'}}</view>
						<p class="good-description">{{goods.alcoDec}}</p>
						<view class="popup-rank">
							<p style="font-size: 26rpx;">选取规格</p>
							<view class="popup-tags">
								<text class="tag-item" style="margin-right: 30rpx;"
									:class="selectIndex==index+1?'select-tag':''" @click="selectHandler(index)"
									v-for="item,index in goods.alcoholsSkuList" :key='item.id'>{{item.skuCap}}</text>
							</view>
						</view>
					</view>
				</scroll-view>
				<view class="popup-footer">
					<view class="popup-nav r-flex-3">
						<view class="popup-price">
							{{goods.alcoholsSkuList?'￥'+goods.alcoholsSkuList[selectIndex-1].skuSellprice:'该商品正在上架中'}}
						</view>
						<view class="popup-count r-flex-1">
							<view class="count-left" v-show="number?true:false">
								<img src="/static/images/reduce.png" @click="setCountHandler(0)">
							</view>
							<view class="count-middle">
								{{number>0?number>99?'99+':number:0}}
							</view>
							<view class="count-left">
								<img src="/static/images/add.png" @click="setCountHandler(1)">
							</view>
						</view>
					</view>
					<button type="primary" class="popup-button" @click="closePop"
						:disabled="(number>0 && selectIndex>0 )?false:true">完&nbsp;&nbsp;成</button>
				</view>
			</view>
		</pop>
	</view>

</template>


<script>
	import {
		mapGetters,
		mapMutations,
		mapState,
	} from 'vuex'
	import goodsNav from '@/components/goods-nav/goods-nav.vue'
	import pop from '@/common/ming-pop_1.0.4/components/ming-pop/ming-pop.vue'
	export default {
		components: {
			goodsNav,
			pop,
		},
		data() {
			return {
				current: 1, // 当前选中的分类
				notice: "公告：本店今日有上新，欢迎订购", // 在此作公告提示
				categoryList: [], // 分类数组
				goodList: [], // 商品数组
				selectIndex: 1, // 选取规格，默认一瓶
				number: 1, // pop购物数量
				goods: {}, // 单个商品对象
				openStore: true, // 店铺是否营业
			}
		},
		onShow() {
			this.initCategory();
			this.isWorking();
		},
		onLoad() {
			this.$checkLogin("/pages/classify/classify", "2");
		},
		methods: {
			...mapMutations('m_order', ['updateShopCart']),
			// 初始化分类列表
			initCategory() {
				this.$api.category.reqCategoryList().then(({
					code,
					data
				}) => {
					code === 200 && (this.categoryList = data, this.getProductList())
				})
			},
			// 获取分类列表右侧的商品
			getProductList(id = 1) {
				this.$api.category.reqProductList(id).then(({
					code,
					data
				}) => {
					code === 200 && (this.goodList = data)
				})
			},
			// 点击分类列表项的回调
			clickLeft(item) {
				(this.current !== item.id) && this.getProductList(item.id)
				this.current = item.id
			},
			// 选规格tags按钮的回调
			selectHandler(index) {
				this.selectIndex = index + 1;
			},
			// 数量+/-按钮的回调
			setCountHandler(which) {
				if (this.number >= this.goods.alcoholsSkuList[this.selectIndex - 1].skuStock) {
					uni.showToast({
						title: "库存不存",
						icon: 'error'
					})
				} else {
					this.number += which === 1 ? 1 : (-1);
				}
			},
			// 显示规格弹窗
			showPop(item) {
				this.$refs.pop.show();
				this.goods = {};
				this.goods = item.alcoholsSkuList.length && {
					...item
				}
			},
			// 规格弹窗完成按钮的回调
			closePop() {
				// 加入购物车
				this.updateShopCart({
					...this.goods,
					sku_id: this.selectIndex || 1,
					number: this.number || 1,
				});
				this.number = 1;
				this.$refs.pop.close(); // 关闭规格弹窗
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
		}
	}
</script>

<style lang="scss" scoped>
	.left__active {
		background-color: #fff;
	}

	.classify__container {
		// height: calc(100vh - 44px - 62.78px);
		height: 100vh;
		overflow: hidden;
		position: relative;
		background-color: #eee;

		.classify__body {
			// height: calc(100vh - 44px - 62.78px - 44px - 30px - 3px);
			height: 100vh;
			overflow: hidden;
			background-color: #fff;

			.left__scroll {
				height: inherit;
				width: 166rpx;
				min-width: 52px;
				max-width: 150px;
				background-color: #f6f6f6;

				.menu__item {
					width: inherit;
					height: 90rpx;
					line-height: 90rpx;
					border: 1px solid #eee;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
					color: #444;
					font-size: 32rpx;
					text-align: center;
				}

			}

			.right__scroll {
				flex: 1;
				height: inherit;

				.good__item {
					padding: 20rpx 20rpx;

					.good__img {
						margin-right: 20rpx;
						width: 150rpx;
						height: 150rpx;

						img {
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

						.rank__button {
							font-size: 28rpx;
							color: #fff;
							padding: 4rpx 16rpx;
							border-radius: 10rpx;
							background-color: #335eea;
						}
					}
				}
			}
		}

		#body-btn {
			position: absolute;
			bottom: 0;
			left: 0;
			box-sizing: border-box;
		}
	}

	.good-popup {
		margin-top: 10px;
		width: 100%;
		height: 100%;
		position: relative;

		.popup-img {
			display: block;
			width: 100%;
			height: 300rpx;
			max-height: 300rpx;
			margin: 20rpx 0;
			border-radius: 10rpx;
		}

		.good-description {
			font-size: 24rpx;
			margin: 10rpx 0 30rpx 0;
		}

		.popup-rank {
			.popup-tags {
				margin: 20rpx 0;

				.tag-item {
					padding: 10rpx 30rpx;
					font-size: 26rpx;
					border: 1px solid #335eea;
					border-radius: 8rpx;
				}
			}

		}

		.popup-footer {
			position: absolute;
			left: 0rpx;
			// bottom: 62.78px;
			bottom: 0rpx;
			width: 100%;
			border-top: 1px solid #eee;
			background-color: #fff;
			box-sizing: border-box;

			.popup-price {
				color: #000;
				font-size: 32rpx;
				font-weight: bold;
			}

			.popup-nav {
				margin: 15rpx 0;

				.popup-count {
					view {
						width: 48rpx;
						height: 48rpx;

						img {
							width: 100%;
							height: 100%;
						}
					}

					.count-middle {
						// width: auto;
						width: 18.77px;
						margin: 0 20rpx;
					}

				}
			}

			.popup-button {
				// height: 80rpx;
				// line-height: 80rpx;
				// letter-spacing: 60rpx;
			}
		}
	}

	.select-tag {
		color: #fff;
		background-color: #335eea;
	}
</style>
