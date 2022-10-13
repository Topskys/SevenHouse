<template>
	<view class="content">
		<scroll-view class="menu-wrapper" scroll-y :scroll-top="left_scroll" scroll-with-animation="true">
			<view class="left-content">
				<view style="position: relative;" v-for="(item,index) in goods" :key="index" ref="menuList"
					@click="select(index)" :class="{'current': currentIndex == index}">
					<view class="menu-item">{{item.name}}</view>
					<text class="allcount" v-if="getAllCount>=item.count&&item.count>0">{{item.count}}</text>
				</view>
			</view>
		</scroll-view>
		<!-- 右侧滚动 -->
		<scroll-view class="foods-wrapper" scroll-y :style="'height:'+windows_height+'px'" :scroll-top="foodSTop"
			@scroll="myscroll" scroll-with-animation="true">
			<view ref="foodsWrapper">
				<view ref="foodList" class="foods" v-for="(item, i) in goods" :key="i">
					<view class="food-title" style="background: #f3f5f7">
						{{item.name}}
					</view>
					<view class="food" v-for="(food, index) in item.foods" :key="index">
						<image :src="food.img" mode="" style="width: 75px;height: 75px;margin-top: 6px;"></image>
						<view class="food-info">
							<text style="font-size: 15px;margin-top: 2px;">{{food.name}}</text>
							<text style="font-size: 13px;margin: 2px 0;">{{food.description}}</text>
							<text style="font-size: 13px;margin: 2px 0 4px;">月售{{food.sellCount}}</text>
							<!-- 加减 -->
							<view class="food-btm">
								<text class="food-price">￥{{food.price}}</text>
								<cartcontrol :food="food" @add="addCart" @dec="decreaseCart"></cartcontrol>
							</view>
						</view>
					</view>
				</view>
			</view>
		</scroll-view>
		<shopcart :goods="goods" @add="addCart" @dec="decreaseCart" @delAll="delAll"></shopcart>
	</view>
</template>

<script>
	import shopcart from '@/common/vincent-sideNavigation_0.1/components/shopcart.vue';
	import cartcontrol from '@/common/vincent-sideNavigation_0.1/components/cartcontrol.vue'
	import Vue from 'vue'

	export default {
		data() {
			return {
				title: 'Hello',
				goods: [{
						"name": "热销",
						"foods": [{

								"name": "南瓜粥",
								"price": 9.22,
								"oldPrice": "",
								"description": "食材：大米，南瓜",
								"sellCount": 229,
								"img": '../../static/nanguaz.jpg'

							}, {
								"name": "小米粥",
								"price": 9.8,
								"oldPrice": "",
								"description": "食材：小米",
								"sellCount": 239,
								"img": '../../static/xiaomi.jpg'

							},
							{
								"name": "油条",
								"price": 1.88,
								"oldPrice": "",
								"description": "食材：油条",
								"sellCount": 229,
								"img": '../../static/youtiao.jpg'

							}
						]
					},
					{
						"name": "折扣",
						"foods": [{
							"name": "油条1只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "套餐",
						"foods": [{
							"name": "油条2只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥2",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "套餐2",
						"foods": [{
							"name": "油条3只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥3",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "热销2",
						"foods": [{
							"name": "南瓜粥1",
							"price": 9.22,
							"oldPrice": "",
							"description": "食材：大米，南瓜",
							"sellCount": 229,
							"img": '../../static/nanguaz.jpg'

						}, {
							"name": "小米粥1",
							"price": 9.8,
							"oldPrice": "",
							"description": "食材：小米",
							"sellCount": 239,
							"img": '../../static/xiaomi.jpg'

						}]
					},
					{
						"name": "折扣2",
						"foods": [{
							"name": "油条4只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥4",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "套餐3",
						"foods": [{
							"name": "油条5只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥5",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "套餐4",
						"foods": [{
							"name": "油条6只",
							"price": 1.81,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥6",
							"price": 9.91,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "热销3",
						"foods": [{
							"name": "南瓜粥3",
							"price": 9.21,
							"oldPrice": "",
							"description": "食材：大米，南瓜",
							"sellCount": 229,
							"img": '../../static/nanguaz.jpg'

						}, {
							"name": "小米粥3",
							"price": 9.78,
							"oldPrice": "",
							"description": "食材：小米",
							"sellCount": 239,
							"img": '../../static/xiaomi.jpg'

						}]
					},
					{
						"name": "折扣3",
						"foods": [{
							"name": "油条7只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥7",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "套餐6",
						"foods": [{
							"name": "油条8只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥8",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "套餐7",
						"foods": [{
							"name": "油条9只",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥9",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "热销4",
						"foods": [{
							"name": "南瓜粥4",
							"price": 9.22,
							"oldPrice": "",
							"description": "食材：大米，南瓜",
							"sellCount": 229,
							"img": '../../static/nanguaz.jpg'

						}, {
							"name": "小米粥4",
							"price": 9.8,
							"oldPrice": "",
							"description": "食材：小米",
							"sellCount": 239,
							"img": '../../static/xiaomi.jpg'

						}]
					},
					{
						"name": "折扣4",
						"foods": [{
							"name": "油条10只1",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 229,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥101",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 239,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "折扣5",
						"foods": [{
							"name": "油条10只2",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 2291,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥102",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 2391,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "折扣6",
						"foods": [{
							"name": "油条10只3",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 2292,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥1043",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 2392,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "折扣7",
						"foods": [{
							"name": "油条10只54",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 2293,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥104",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 2393,
							"img": '../../static/tingzaiz.jpg'

						}],

					},
					{
						"name": "折扣8",
						"foods": [{
							"name": "油条10只5",
							"price": 1.88,
							"oldPrice": "",
							"description": "食材：油条",
							"sellCount": 224,
							"img": '../../static/youtiao.jpg'

						}, {
							"name": "艇仔粥105",
							"price": 9.9,
							"oldPrice": "",
							"description": "食材：瘦肉，干贝，花生等",
							"sellCount": 2394,
							"img": '../../static/tingzaiz.jpg'

						}],

					}

				],

				windows_height: 0, //屏幕高度
				foodSTop: 0, //右侧的滑动值
				currentIndex: 0,

				last: 0,
				right_height: [], //右侧每个内容的高度集合
				select_index: 0,
				left_height: 0, //左侧总高度
				left_scroll: 0, //左侧滑动值
				catrgoryList: [],
				timeout: null
			}

		},
		components: {
			shopcart,
			cartcontrol
		},
		onLoad() {
			this.windows_height = Number(uni.getSystemInfoSync().windowHeight) - 55;
			setTimeout(() => {
				this.getHeightList();
			}, 1000)
		},

		computed: {
			getList() {
				let result = [];
				this.goods.forEach((good) => {

					good.foods.forEach((food) => {

						if (food.count) {
							result.push(food)
						}
					})
				})
				return result

			},
			// 获得购物车所有商品数量
			getAllCount: function(item) {
				let result = [];
				let count = 0;

				for (let i = 0; i < this.goods.length; i++) {
					count = 0;
					this.goods[i].foods.forEach((food) => {
						if (food.count >= 0) {
							count += food.count
							Vue.set(this.goods[i], 'count', count)
						}
					})
					result.push(count)
				}

				result.sort(function(a, b) {
					return a - b;
				})
				count = result[result.length - 1]
				return count;
			},

		},
		methods: {
			// 点击侧边栏
			select(index) {
				this.currentIndex = index;
				this.setScrollH(index);
			},

			// 设置点击侧边栏右边滚动的高度
			setScrollH: function(index) {
				var that = this;
				let height = 0;
				var query = uni.createSelectorQuery();
				let foods = query.selectAll('.foods');

				this.$nextTick(function() {
					foods.fields({
						size: true
					}, data => {
						if (index == 0) {
							that.foodSTop = 0;
						}
						for (let i = 0; i < index; i++) {

							height += parseInt(data[i].height);
							// console.log('fh', foods);
							that.foodSTop = height;
							// console.log(that.foodSTop)
						}

					}).exec();
				})

			},

			addCart: function(item) {
				if (item.count >= 0) {
					item.count++
					this.goods.forEach((good) => {
						good.foods.forEach((food) => {
							// 根据名字添加购物车,实际环境根据需要更改
							if (item.name == food.name)
								food.count = item.count
						})
					})
				} else {
					console.log('add')
					this.goods.forEach((good) => {
						good.foods.forEach((food) => {
							if (item.name == food.name)
								Vue.set(food, 'count', 1)
						})
					})
				}
			},
			decreaseCart(item) {
				if (item.count) {
					item.count--
					this.goods.forEach((good) => {
						good.foods.forEach((food) => {
							if (item.name == food.name)
								food.count = item.count
						})
					})
				}
			},
			// 清空购物车
			delAll() {
				this.goods.forEach((good) => {
					good.foods.forEach((food) => {
						if (food.count) {
							food.count = 0
						}
					})
				})
			},
			getHeightList() {
				let _this = this;
				let selectorQuery = uni.createSelectorQuery().in(this);
				selectorQuery.select('.left-content').boundingClientRect(function(rects) {
					_this.left_height = rects.height;
				});
				selectorQuery.selectAll('.foods').boundingClientRect(function(rects) {
					_this.right_height = rects.map((item) => item.top);
				}).exec();
			},
			myscroll(e) {
				//引入节流	
				let right_content_height = e.detail.scrollHeight - this.windows_height;
				if (right_content_height == e.detail.scrollTop) {
					return;
				}
				let scroll_top = e.detail.scrollTop + 110; //110是banner图的高度
				//判断当前的scrollTop在哪个区间内;
				let now = +new Date();
				if (now - this.last > 100) {
					this.last = now;
					let active_index = this.right_height.findIndex((value, index, arr) => value <= scroll_top &&
						scroll_top < arr[index + 1]);
					this.currentIndex = active_index;
					if (this.left_height - this.windows_height) {
						//如果有超出部分
						let diff = this.left_height - this.windows_height
						this.left_scroll = Math.round((active_index * diff) / (this.goods.length - 1))
					}
				}
			}
		}
	}
</script>

<style>
	.content {
		display: flex;
		position: absolute;
		top: 0;
		bottom: 55px;
		width: 100%;
		overflow: hidden;
	}

	.current {
		position: relative;
		z-index: 0;
		background-color: #fff;
		color: #00A0DC;
		border-left: 5px solid #00A0DC;
	}

	.menu-wrapper {
		text-align: center;
		width: 22%;
		display: flex;
		flex-direction: column;
		background: #f3f5f7;

	}

	.menu-item {
		width: 100%;
		display: flex;
		align-items: center;
		justify-content: center;
		height: 50px;
	}

	.allcount {
		position: absolute;
		right: 6px;
		top: 8px;
		display: inline-block;
		padding: 0 4px;
		font-size: 9px;
		line-height: 16px;
		font-weight: 400;
		border-radius: 50%;
		background-color: #f01414;
		color: #ffffff;
	}

	.foods-wrapper {
		margin-left: 4px;
		width: 78%;
	}

	.food,
	.food-btm,
	.content {
		display: flex;
		flex-direction: row;
	}

	.food-title {
		padding: 2px 0;
	}

	.food-info {
		margin-left: 10px;
		margin-right: 16px;
		display: flex;
		flex-direction: column;
		flex: 2;
	}

	.food-btm {
		justify-content: space-between;
	}

	.food-price {
		color: #f01414;
		font-size: 16px;
	}
</style>
