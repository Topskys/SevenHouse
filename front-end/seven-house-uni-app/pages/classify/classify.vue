<template>
	<!-- <scroll-view scroll-y="true" style="min-height: calc(100vh - 44px - 62.78px );  overflow: hidden;">
		<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000">
			<swiper-item v-for="(item,index) in banners" :key="index">
				<img :src="item.imgUrl" alt="404" style="width: 100%;height: 100%;" />
			</swiper-item>
		</swiper>
		<category-list :height="500" :offsetTop="offsetTop" :defaultActive="defaultActive" :categoryList="categoryList"
			@categoryMainClick="categoryMainClick" @categorySubClick="categorySubClick"></category-list>

	</scroll-view> -->
	<view class="classify__container">
		<marquee vspace='20' hspace='30' width='100%' bgcolor="#ff7250" height='30'
			style="line-height: 30px;color:#eee;">
			{{notice}}
		</marquee>
		<view class="classify__body r-flex-1">
			<scroll-view scroll-y="true" class='left__scroll'>
				<view v-for="item,index in categoryList" :key='item.index' @click='clickLeft(item)'
					:class='index==current?"left__active":""' class="menu__item">
					{{item.title}}
				</view>
			</scroll-view>
			<scroll-view scroll-y="true" class='right__scroll'>
				<!-- <view class="good__item r-flex-1" v-for="item in 20" :key='item' @click='clickRight(item)'> -->
				<view class="good__item r-flex-1" v-for="item in goodList" :key='item.id'>
					<view class="good__img">
						<img src="static/images/news1.png" />
					</view>
					<view class="good__desc ">
						<p class='good__name'>{{item.name}}</p>
						<p class='good__intro'>{{item.description}}</p>
						<view class="r-flex-3" style="margin-top: 5px;">
							<view class="left">
								<text style="color: #ff7250;font-size: 13px;">￥</text><text
									style="color: #ff7250;font-size: 16px;font-weight: bold;">{{item.price}}</text>
							</view>
							<wmNumberBox destination="body-btn" model="2" @change="numberBoxClick($event,item)">
							</wmNumberBox>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
		<goods-nav id="body-btn"></goods-nav>
	</view>

</template>


<script>
	import {
		mapGetters,
		mapMutations,
		mapState,
	} from 'vuex'
	import categoryList from '@/common/sideCategoryList_1.0.3/components/zhoujessie-SideCategoryList/category-list.vue'
	import wmNumberBox from "@/common/wm-numberBox_1.2/components/wm-numberBox/wm-number-box.vue"
	import goodsNav from '@/components/goods-nav/goods-nav.vue'
	export default {
		components: {
			categoryList,
			wmNumberBox,
			goodsNav,
		},
		data() {
			return {
				current: 0,
				prevOrder: [
					// {
					// id: 6,
					// orderId: '',
					// discount: 0,
					// status: "",
					// price: 3299,
					// payPrice: 0 || 3299,
					// name: "贵州茅台酒套餐",
					// time: '',
					// save: false, // 0 || 1
					// pay: false,
					// imgUrl: "/static/images/banner1.png",
					// delete: false,
					// count: 0, // 订购数量
					// payWay: "",
					// stock: 100, //库存
					// description: "简要描述",
					// classification: {
					// 	index: 0,
					// 	title: "该商品所属的类别"
					// }
					// },
				], // 预处理订单

				notice: "公告：本店今日有上新，欢迎订购", // 在此作公告和修业提示
				categoryList: [],
				goodList: [],
			}
		},
		computed: {
			// ...mapState({
			// 	order: (state) => state.m_order.order
			// }),
		},
		created() {
			this.initCategory()
		},
		onLoad() {
			setTimeout(this.$checkLogin("/pages/classify/classify", "2"), 5000)
		},
		methods: {
			...mapMutations('m_order', ['updateOrder', 'deleteOrder']),
			// 初始化分类列表
			initCategory() {
				this.$api.category.reqCategoryList().then(({
					code,
					data
				}) => {
					code === 200 && (this.categoryList = data.category.categoryList, this.getProductList())
				})
			},
			// 获取分类列表右侧的商品
			getProductList(index = 0) {
				this.$api.category.reqProductList({
					index
				}).then(
					({
						code,
						data
					}) => {
						code === 200 && (this.goodList = data.goodList)
					})
			},
			// 点击分类列表项的回调
			clickLeft(item) {
				(this.current !== item.index) && this.getProductList(item.index)
				this.current = item.index
			},
			// 订单数量的回调
			numberBoxClick(e, obj) {
				// 添加延时
				setTimeout(() => {
					obj.count = e.value
					this.updateOrder(obj)
				}, 1000);

			}
		}
	}
</script>

<style lang="scss" scoped>
	.left__active {
		background-color: #fff;
	}

	.classify__container {
		height: calc(100vh - 44px - 62.78px);
		overflow: hidden;
		position: relative;
		background-color: #eee;

		.classify__body {
			height: calc(100vh - 44px - 62.78px - 44px - 30px - 3px);
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
					font-size: 16px;
					text-align: center;
				}

			}

			.right__scroll {
				flex: 1;
				height: inherit;

				.good__item {
					padding: 10px 20rpx;

					.good__img {
						margin-right: 10px;
						width: 75px;
						height: 75px;

						img {
							width: 100%;
							height: 100%;
							border-radius: 5px;
						}
					}

					.good__desc {
						flex: 1;

						.good__name {
							color: #333;
							font-size: 15px;
						}

						.good__intro {
							color: #777;
							font-size: 12px;
							margin-top: 5px;
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
</style>
