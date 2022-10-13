<template>
	<view class="register__container" style="min-height: 100vh;padding:0 30rpx">
		<uni-forms :modelValue="form" ref="form" :rules="rules" validate-trigger="bind">
			<uni-forms-item name="nickname" class="uni-form-item" label="用户名:" required>
				<input class="uni-input" v-model="form.nickname" placeholder="请输入用户名" />
			</uni-forms-item>
			<uni-forms-item name="phone" class="uni-form-item" label="手机号:" required>
				<input class="uni-input" v-model="form.phone" type="number" placeholder="请输入手机号" maxlength=11 />
			</uni-forms-item>
			<uni-forms-item name="email" class="uni-form-item" label="邮箱:" required>

				<input class="uni-input" v-model="form.email" placeholder="请输入邮箱" />
			</uni-forms-item>
			<uni-forms-item name="password" class="uni-form-item" label="密码:" required>
				<input class="uni-input" password type="text" v-model="form.password" placeholder="请输入密码" />
			</uni-forms-item>
		</uni-forms>
		<button @click="submit" style="margin-top: 100rpx;background-color: #42b983;letter-spacing: 4rpx;">注册</button>
		<view class="register__negotitate" style="color: #999;font-size: 28rpx;margin-top: 40rpx;text-align: center;">
			登录注册即代表阅读并同意<a href="https://uniapp.dcloud.net.cn/" style="color: #335eea;text-decoration: none;">服务协议</a>
		</view>
	</view>
</template>

<script>
	import uniForms from '@/components/uni-forms_1.4.8/components/uni-forms/uni-forms.vue'
	import uniFormsItem from '@/components/uni-forms_1.4.8/components/uni-forms-item/uni-forms-item.vue'
	export default {
		components: {
			uniForms,
			uniFormsItem,
		},
		data() {
			return {
				form: {
					phone: '',
					nickname: "",
					password: "",
					email: '',
				},
				rules: {
					nickname: {
						rules: [{
								required: true,
								errorMessage: '请输入用户名',
							},
							{
								minLength: 2,
								maxLength: 10,
								errorMessage: '用户名长度必须是 {minLength} 到 {maxLength} 个字符',
							}
						]
					},
					// 对email字段进行必填验证
					email: {
						rules: [{
							required: true,
							errorMessage: '请输入邮箱',
						}, {
							format: 'email',
							errorMessage: '请输入正确的邮箱地址',
						}]
					},
					// 对name字段进行必填验证
					password: {
						rules: [{
								required: true,
								errorMessage: '请输入密码',
							},
							{
								minLength: 8,
								maxLength: 16,
								errorMessage: '密码长度必须是 {minLength} 到 {maxLength} 个字符',
							}
						]
					},
					phone: {
						rules: [{
								required: true,
								errorMessage: '请输入手机号',
							},
							{
								format: 'phone',
								errorMessage: '请输入正确的手机号',
							}
						]
					},
				}
			}
		},
		onReady() {
			// 需要在onReady中设置规则
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			// 提交登录表单
			submit(form) {
				this.$refs.form.validate().then(res => {
					console.log('表单数据信息：', res);
					uni.showModal({
						content: '表单数据内容：' + JSON.stringify(res),
						showCancel: false
					});
				}).catch(err => {
					console.log('表单错误信息：', err);
				})
			},
			// 前往注册
			navRegister() {
				uni.navigateTo({
					url: '../register/register'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.uni-form-item {

		.uni-input {
			border-bottom: 1px solid #dcdfe6;
			// border-radius: 10rpx;
			padding: 10rpx;
		}

		.title {
			// padding-bottom: 20rpx;
			padding: 20rpx 0;
		}
	}

	.uni-forms-item__label {
		padding: 0 !important;
	}
</style>
