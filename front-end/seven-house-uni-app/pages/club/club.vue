<template>
	<view style="text-align: center;height: calc(100vh - 44px);line-height: calc(100vh - 44px);">
		<img src="static/images/official-club.png" style="width: 50%;">
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		onLoad() {
			// 检查登录信息，参数：backPath, backType[1 : redirectTo 2 : switchTab]
			setTimeout(this.$checkLogin("/pages/club/club", "1"), 5000)
		},
	}
</script>

<style>

</style>
