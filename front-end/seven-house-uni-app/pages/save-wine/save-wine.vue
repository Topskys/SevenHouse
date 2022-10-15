<template>
	<scroll-view scroll-y="true" style="height: calc(100vh - 44px ); ">
		<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in 10" :key='item'>
			<view class="order__item">
				<view class="order__body r-flex-1 mt10">
					<view class="body__left">
						<img src="static/images/banner1.png" />
					</view>
					<view class="body__right">
						<b class="order__title">贵州茅台酒套餐</b>
						<view class="r-flex-3">
							<p style="font-size: 12px;">下单时间：2022-10-13</p>
							<p style="font-size: 12px;">x1</p>
						</view>
					</view>
				</view>
				<view class="order__footer  r-flex-6" style="margin-top: 10px ;">
					<view>
						<button type="primary" @click='takeout(item)' size='mini'>取 酒</button>
					</view>
				</view>
			</view>
		</uni-card>

	</scroll-view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	export default {
		data() {
			return {
				saveWine: [],
			}
		},
		components: {
			uniCard,
		},
		computed: {
			...mapState({
				userInfo: (state) => state.user.userInfo
			})
		},
		methods: {
			// 取酒按钮的回调
			takeout(item) {
				this.$api.user.reqTakeoutWine(item).then(({
					code,
					data
				}) => {
					uni.showToast({
						title: data.msg,
						icon: code === 200 ? 'success' : 'error'
					})
				})
			}
		},
		watch: {
			// userInfo: () => [this.saveWine = this.userInfo.order.saveWine]
		}
	}
</script>

<style lang="scss" scoped>
	.order__body {
		.body__left {
			margin-right: 10px;

			img {
				width: 75px;
				height: 75px;
				border-radius: 10px;
			}
		}

		.body__right {
			flex: 1;

			.order__title {
				color: #000;
				font-size: 16px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}
		}
	}
</style>
