<template>
	<view class="tab-pane">
		<template v-if="show">
			<slot></slot>
		</template>
		<template v-else>
			<view style="width: 750rpx;height: 0;"></view>
		</template>
	</view>
</template>
<script setup>
	// // #ifdef VUE3
	// import { ref, watch } from 'vue';
	// const data_props = defineProps({
	// 	label: [String, Number],
	// 	name: [String, Number]
	// });
	// const show = ref(false);
	// let timer = 0;
	// const watchTabKey = `watchTabValue_${data_props.name.split('_')[0]}`;
	// console.log(watchTabKey)
	// watch(() => data_props.label, (newValue, oldValue) => {
	// 	uni.$emit(watchTabKey, { newValue: newValue, oldValue: oldValue, name: data_props.name });
	// }, { immediate: true });
	// uni.$on('putChange', item => {
	// 	if (timer) clearTimeout(timer);
	// 	show.value = true;
	// 	timer = setTimeout(() => {
	// 		show.value = data_props.name == item.name;
	// 	}, item.duration)
	// })
	// // #endif
</script>
<script>
	export default {
		props: ['label', 'name'],
		data() {
			return {
				timer: 0,
				show: false,
				watchTabKey: `watchTabValue_${this.name.split('_')[0]}`,
				putChangeKey: `putChange_${this.name.split('_')[0]}`
			}
		},
		created() {
			uni.$on(this.putChangeKey, item => {
				if (this.timer) clearTimeout(this.timer);
				this.show = true;
				this.timer = setTimeout(() => {
					this.show = this.name == item.name;
				}, item.duration)
			})
		},
		watch: {
			label: {
				deep: true,
				handler(newValue, oldValue) {
					uni.$emit(this.watchTabKey, { newValue: newValue, oldValue: oldValue, name: this.name });
				},
				immediate: true
			}
		}
	}
</script>
<style lang="scss" scoped>
	.tab-pane {
		width: 750rpx;
		// #ifndef APP-PLUS-NVUE
		flex-shrink: 0;
		// #endif
	}
</style>
