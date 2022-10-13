<template>
	<view class="login__container" style="min-height: 100vh;background: #fff ;padding:0 30rpx">
		<uni-forms :modelValue="form" ref="form" :rules="rules" validate-trigger="bind">
			<uni-forms-item name="email" class="uni-form-item">
				<view class="title">邮箱</view>
				<input class="uni-input" v-model="form.email" placeholder="请输入邮箱" />
			</uni-forms-item>
			<uni-forms-item name="password" class="uni-form-item">
				<view style="padding-bottom: 20rpx;">密码</view>
				<input class="uni-input" password type="text" v-model="form.password" placeholder="请输入密码" />
			</uni-forms-item>
		</uni-forms>
		<button @click="submit" style="margin-top: 100rpx;background-color: #42b983;letter-spacing: 4rpx;">登录</button>
		<button @click="navRegister" style="margin-top: 40rpx;letter-spacing: 4rpx;">注册</button>
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
					email: '',
					password: "",
				},
				rules: {
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
			padding: 20rpx;
			border-radius: 10rpx;
			background-color: #fff;
			border: 1px solid #dcdfe6;

			// input::focus {
			// 	border: 1px solid #335eea;
			// }
		}



		.title {
			// padding-bottom: 20rpx;
			padding: 20rpx 0;
		}
	}

	.uni-form-item .uni-input:focus {
		outline: 1px solid #335eea;
		border: 1px solid #335eea;
		border-color: #335eea;
	}
</style>
