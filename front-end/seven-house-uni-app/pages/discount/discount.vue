<template>
	<scroll-view scroll-y="true" style="height: calc(100vh - 44px ); ">
		<uni-card :isShadow="false" style='border-radius: 20rpx;' v-for="item in parseInt(1 + Math.random()*99)"
			:key='item'>
			<view class="order__item r-flex-3">
				<view class="item__left">
					<p style='font-size: 18px;color: #000;'>{{discount[parseInt(Math.random()*discount.length)]}}</p>
					<view class="r-flex-1" style="color: red;margin-top: 5px;">
						<span style='font-size: 18px;z-index: 1;'>{{(Math.random()*100).toFixed(0)}}</span>
						<span style='font-size: 12px;'>元 今日限领{{(Math.random()*30).toFixed(0)}}张</span>
					</view>
				</view>
				<view class="item__right">
					<button type="warn" @click='takeout(item)' size='mini'>免费领取</button>
				</view>
			</view>
		</uni-card>

	</scroll-view>
</template>

<script>
	import uniCard from '@/common/uni-card_1.3.1/components/uni-card/uni-card.vue'
	import {
		mapState
	} from 'vuex'
	export default {
		data() {
			return {
				discount: ['洒酒节红包来袭', '新品来袭', '明晚开播，敬请期待', '世纪东方', '要保持度']
			}
		},
		components: {
			uniCard,
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			this.$checkLogin("/pages/discount/discount", "1");
		},
		computed: {
			...mapState({
				userInfo: (state) => state.m_user.userInfo
			})
		},
		created() {
			// this.getDiscountList()
		},
		methods: {
			getDiscountList() {
				this.$api.user.reqDiscountList().then(({
					code,
					data
				}) => {
					code == 200 && (this.discount = data.list)
				})
			},
			// 取酒按钮的回调
			takeout(item) {
				item && this.$api.user.reqTakeDiscount({
					item
				}).then(({
					code,
					data
				}) => {
					// this.getDiscountList()
					// console.log(data)
					uni.showToast({
						title: data.msg,
						icon: code === 200 ? 'success' : 'error'
					})
				})
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
