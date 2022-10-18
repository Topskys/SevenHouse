<template>
	<scroll-view scroll-y="true" style="height: calc(100vh - 44px ); ">
		<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in saveWine" :key='item.id'>
			<view class="order__item">
				<view class="order__body r-flex-1 mt10">
					<view class="body__left">
						<img :src="item.imgUrl" />
					</view>
					<view class="body__right">
						<b class="order__title">{{item.name}}</b>
						<view class="r-flex-3">
							<p style="font-size: 12px;">下单时间：{{item.time}}</p>
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
		created() {
			this.initSaveWine()
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			setTimeout(this.$checkLogin("/pages/save-wine/save-wine", "1"), 5000)
		},
		methods: {
			// 初始化存酒列表
			initSaveWine() {
				this.$api.user.reqSaveWineList().then(({
					code,
					data
				}) => {
					code == 200 && (this.saveWine = data.list)
				})
			},
			// 取酒按钮的回调
			takeout(item) {
				this.$api.user.reqTakeoutWine(item).then(({
					code,
					data
				}) => {
					this.initSaveWine()
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
