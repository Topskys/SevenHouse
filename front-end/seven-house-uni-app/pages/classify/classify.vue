<template>
	<scroll-view scroll-y="true" style="min-height: calc(100vh - 44px - 62.78px );  overflow: hidden;">
		<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000">
			<swiper-item v-for="(item,index) in banners" :key="index">
				<img :src="item.imgUrl" alt="404" style="width: 100%;height: 100%;" />
			</swiper-item>
		</swiper>
		<category-list :height="500" :offsetTop="offsetTop" :defaultActive="defaultActive" :categoryList="categoryList"
			@categoryMainClick="categoryMainClick" @categorySubClick="categorySubClick"></category-list>

	</scroll-view>
</template>


<script>
	import categoryList from '@/common/sideCategoryList_1.0.3/components/zhoujessie-SideCategoryList/category-list.vue'

	export default {
		components: {
			categoryList,
		},
		data() {
			return {
				offsetTop: 150,
				defaultActive: 0,
				categoryList: [{
						name: '分类1',
						subCategoryList: [{
								name: '子类1',
								label: "商品1",
								imgSrc: 'static/images/news1.png'
							},
							{
								name: '子类2',
								label: "商品1",
								imgSrc: 'static/images/news1.png'
							},
							{
								name: '子类3',
								label: "商品1",
								imgSrc: 'static/images/news1.png'
							},
							{
								name: '子类4',
							},
							{
								name: '子类5',
							},
							{
								name: '子类6',
							}
						]
					},
					{
						name: '分类2',
						subCategoryList: [{
							name: '子类1',
						}]
					},
					{
						name: '分类3',
						subCategoryList: [{
							name: '子类1',
						}]
					}
				],
				banners: [{
						imgUrl: 'static/images/news1.png',
					},
					{
						imgUrl: 'static/images/news2.png',
					},
					{
						imgUrl: 'static/images/news3.png',
					},
				]
			}
		},

		methods: {
			categoryMainClick(val) {
				console.log(val)
			},
			categorySubClick(val) {
				console.log(val)
			}
		}
	}
</script>
