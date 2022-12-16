<template>
	<scroll-view scroll-y="true" style="height:100vh; ">
		<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item,i in saveWine" :key='i'>
			<view class="order__item">
				<view class="order__body r-flex-1 mt10">
					<view class="body__left">
						<img :src="item.alcoImage" />
					</view>
					<view class="body__right">
						<b class="order__title">{{item.alcoName}}</b>
						<view class="r-flex-3">
							<p style="font-size: 24rpx;">下单时间：{{item.createTime|dateFilter}}</p>
							<p style="font-size: 24rpx;">数量：{{item.depNum}}</p>
						</view>
					</view>
				</view>
				<view class="order__footer  r-flex-6" style="margin-top: 20rpx ;">
					<view>
						<button type="primary" @click='showPop(item)' size='mini'>取 酒</button>
					</view>
				</view>
			</view>
		</uni-card>
		<pop ref="pop" direction="center" :is_close="true" :is_mask="true" :width="80" height="30vh" :maskFun="true">
			<view class="popup-take">
				<view class="popup-head">取酒数量</view>
				<input v-model="takeCount" type="number" focus :maxlength="wineItem.alcoCap" placeholder="输入取酒的数量" />
				<button type="primary" @click='takeout' size='mini' class="popup-button">确定</button>
			</view>
		</pop>
	</scroll-view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	import pop from '@/common/ming-pop_1.0.4/components/ming-pop/ming-pop.vue'
	import {
		dateFormat
	} from '@/utils/index.js'
	export default {
		data() {
			return {
				saveWine: [],
				takeCount: 1, // 取酒数量
				wineItem: {}, // 临时
			}
		},
		components: {
			uniCard,
			pop,
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
			})
		},
		created() {
			this.initSaveWine()
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			this.$checkLogin("/pages/save-wine/save-wine", "1");
		},
		methods: {
			// 初始化存酒列表
			initSaveWine() {
				this.$api.user.reqSaveWineList(this.userInfo.id).then(({
					code,
					data,
					msg
				}) => {
					code === 200 && (this.saveWine = data)
				})
			},
			// 取酒按钮的回调
			takeout() {
				this.$api.user.reqTakeoutWine({
					id: this.wineItem.id,
					depNum: this.takeCount || 1
				}).then(({
					code,
					data,
					msg
				}) => {
					this.initSaveWine()
					uni.showToast({
						title: msg,
						icon: code === 200 ? 'success' : 'error'
					})
					this.$refs.pop.close();
				})
			},
			showPop(item) {
				this.$refs.pop.show();
				this.wineItem = {
					...item
				}
			},
		},
		watch: {
			// userInfo: () => [this.saveWine = this.userInfo.order.saveWine]
		},
		filters: {
			// 私有局部过滤器，只能在 当前 VM 对象所控制的 View 区域进行使用
			dateFilter(val) {
				return dateFormat(val, 'yyyy-mm-dd');
			}
		}
	}
</script>

<style lang="scss" scoped>
	.order__body {
		.body__left {
			margin-right: 20rpx;

			img {
				width: 150rpx;
				height: 150rpx;
				border-radius: 20rpx;
			}
		}

		.body__right {
			flex: 1;

			.order__title {
				color: #000;
				font-size: 32rpx;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}
		}
	}

	.popup-take {
		position: relative;
		width: 100%;
		height: 100%;

		.popup-head {
			margin: 20rpx 0 50rpx 0;
		}

		input {
			border: 1px solid #eee;
			padding: 10rpx 20rpx;
		}

		.popup-button {
			position: absolute;
			bottom: 20rpx;
			width: 100%;
			height: 80rpx;
			line-height: 80rpx;
			letter-spacing: 20rpx;
		}
	}
</style>
