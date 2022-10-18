<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	/* 条件编译，仅在H5平台生效 */
	body::-webkit-scrollbar,
	html::-webkit-scrollbar {
		display: none;
	}
</style>
