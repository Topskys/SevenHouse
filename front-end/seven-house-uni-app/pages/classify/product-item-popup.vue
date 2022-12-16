<template>
	<z-popup v-model="true" @change="onChange">
		<view class="popup_title">
			<text @click="popupShow = false">取消</text>
			<view>弹窗标题</view>
			<text @click="popupShow = false">确定</text>
		</view>
		<view class="popup_content">
			弹窗内容
		</view>
	</z-popup>

</template>

<script>
	import zPopup from '@/common/z-popup_1.0.1/components/z-popup/z-popup.vue'
	export default {
		name: '',
		props: [],
		components: {
			zPopup
		},
		data() {
			return {
				popupShow: false,
			}
		},
		methods: {
			onChange(e) {
				if (e) {
					uni.showToast({
						title: "打开了弹窗",
						icon: "none"
					});
				} else {
					uni.showToast({
						title: "关闭了弹窗",
						icon: "none"
					});
				}
			}
		},
	}
</script>

<style>
</style>
