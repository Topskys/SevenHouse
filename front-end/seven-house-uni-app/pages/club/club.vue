<template>
	<view style="text-align: center;height: 100vh;line-height: 100vh;">
		<!-- text-align: center;height: calc(100vh - 44px);line-height: calc(100vh - 44px);// h5 -->
		<img src="/static/images/official-club.png" style="width: 50%;">
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
			this.$checkLogin("/pages/club/club", "1");
		},
	}
</script>

<style>

</style>
