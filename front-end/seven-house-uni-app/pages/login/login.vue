<template>
	<view class="login__container">
		<!-- <view class="grid mb20" style="margin-top: 20%;">
			<view class=' pg10-0 ta-c' :class='tab===0?"active":""' @click='tab=0'>登 录</view>
			<view class=' pg10-0 ta-c' :class='tab===0?"":"active"' @click='tab=1'>注 册</view>
		</view> -->
		<view style="height: 10vh"></view>
		<liuyuno-tabs :tabData="tabs" :defaultIndex="tab" @tabClick='tabClick' :config='config' />
		<view style="height: 10vh"></view>
		<!-- 登录 -->
		<view v-show="tab===0">
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
			<button @click="submit" style="margin-top: 100rpx;background-color: #42b983;letter-spacing: 4rpx;">登
				录</button>
			<view class="register__negotitate"
				style="color: #999;font-size: 28rpx;margin-top: 40rpx;text-align: center;">
				登录注册即代表阅读并同意<a href="https://uniapp.dcloud.net.cn/"
					style="color: #335eea;text-decoration: none;">服务协议</a>
			</view>
		</view>
		<!-- 注册 -->
		<register v-show="tab===1"></register>
		<!-- <button @click="navRegister" style="margin-top: 40rpx;letter-spacing: 4rpx;">注 册</button> -->

	</view>
</template>

<script>
	import {
		mapGetters,
		mapMutations,
		mapState,
	} from 'vuex'
	import uniForms from '@/components/uni-forms_1.4.8/components/uni-forms/uni-forms.vue'
	import uniFormsItem from '@/components/uni-forms_1.4.8/components/uni-forms-item/uni-forms-item.vue'
	import register from '@/components/register/register.vue'
	import liuyunoTabs from '@/common/liuyuno-tabs_2.0.2/components/liuyuno-tabs/liuyuno-tabs.vue'
	export default {
		components: {
			uniForms,
			uniFormsItem,
			register,
			liuyunoTabs,
		},
		data() {
			return {
				tab: 0, // 登录注册选项卡
				tabs: ['登录', '注册'],
				config: {
					activeColor: '#335eea',
					underLineColor: '#335eea',
					fontSize: 38,
					underLineHeight: 5,
				},
				backPath: "/pages/index/index",
				backType: "2",
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
		computed: {
			...mapState({
				userInfo: (state) => state.m_user.userInfo,
				token: (state) => state.m_user.token,
			})
		},
		onLoad(options) {
			this.backPath = options.backPath || "/pages/index/index"
			this.backType = options.backType || "2"
		},
		onReady() {
			// 需要在onReady中设置规则
			this.$refs.form.setRules(this.rules)
		},
		methods: {
			...mapMutations('m_user', ['updateUserInfo', 'updateToken']),
			// tabs的回调
			tabClick(val) {
				this.tab = val
			},
			// 提交登录表单
			submit(form) {
				this.$refs.form.validate().then(res => {
					// JSON.stringify(res) !(this.userInfo && this.token) ?
					let token = uni.getStorageSync("token");
					let userInfo = uni.getStorageSync("userInfo");
					!(token && userInfo) ? this.$api.user.reqLogin(res).then(({
						code,
						data,
						msg,
					}) => {
						if (code === 200) {
							// 保存用户信息，跳转页面
							this.updateUserInfo(data)
							// 判断当前路径是否携带跳转参数且返回上一级，否则跳转首页
							this.updateToken(data.token)
							// 跳转
							this.backType == "1" ? uni.redirectTo({
								url: this.backPath
							}) : uni.switchTab({
								url: this.backPath
							})
						} else {
							uni.showToast({
								title: msg,
								icon: 'error'
							});
						}
					}): uni.showToast({
						title: "您已登录",
						icon: 'success'
					});
				}).catch(err => {
					console.log('表单错误信息：', err);
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.login__container {
		// height: calc(100vh - 44px);
		height: 100vh;
		background: #fff;
		padding: 0 30rpx;
		overflow: hidden;
	}

	.active {
		color: #fff;
		background-color: rgb(66, 185, 131);
		// background-color: #845EC2;
		// position: sticky;
		transition: all 0.2s;
	}

	.uni-form-item {
		.uni-input {
			padding: 20rpx;
			border-radius: 10rpx;
			background-color: #fff;
			border: 1px solid #dcdfe6;
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
