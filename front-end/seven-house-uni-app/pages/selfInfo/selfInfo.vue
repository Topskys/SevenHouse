<template>
	<view class="container">
		<view class="ui-all">
			<view class="avatar" @tap="avatarChoose">
				<view class="imgAvatar">
					<view class="iavatar" :style="'background: url('+avater+') no-repeat center/cover #eeeeee;'"></view>
				</view>
				<text v-if="avater">修改头像</text>
				<text v-if="!avater">授权微信</text>
				<button v-if="!avater" open-type="getUserInfo" @tap="getUserInfo" class="getInfo"></button>
			</view>
			<view class="ui-list">
				<text>昵称</text>
				<input type="text" :placeholder="value" :value="nickName" @input="bindnickName"
					placeholder-class="place" />
			</view>
			<view class="ui-list">
				<text>手机号</text>
				<input type="number" :placeholder="value" :value="mobile" @input="bindnickMobile"
					placeholder-class="place" />
				<!-- <input v-if="mobile" type="tel" :placeholder="value" :value="mobile" @input="bindmobile"
					placeholder-class="place" />
				<button v-if="!mobile" open-type="getPhoneNumber" @getphonenumber="getphonenumber"
					class="getInfo bun">授权手机号</button> -->
			</view>
			<view class="ui-list right">
				<text>性别</text>
				<picker @change="bindPickerChange" mode='selector' range-key="name" :value="index" :range="sex">
					<view class="picker">
						{{sex[index].name}}
					</view>
				</picker>
			</view>
			<view class="ui-list">
				<text>收货地址</text>
				<input type="text" :placeholder="value" :value="region" @input="bindnickRegion"
					placeholder-class="place" />
			</view>
			<view class="ui-list right">
				<text>生日</text>
				<picker mode="date" :value="date" @change="bindDateChange">
					<view class="picker">
						{{date}}
					</view>
				</picker>
			</view>
			<view class="ui-list">
				<text>签名</text>
				<textarea :placeholder="value" placeholder-class="place" :value="description"
					@input="binddescription"></textarea>
			</view>
			<button class="save" @tap="savaInfo">保 存 修 改</button>
		</view>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				value: '请填写',
				sex: [{
					id: 1,
					name: '男'
				}, {
					id: 2,
					name: '女'
				}],
				index: 0,
				region: '', //大学城学府南路000号
				date: '请填写',
				avater: 'static/login.png',
				description: '',
				url: '',
				nickName: '',
				mobile: '',
				headimg: ''

			}

		},
		methods: {
			bindPickerChange(e) {
				this.index = e.detail.value;

			},
			bindnickRegion(e) {
				this.region = e.detail.value;

			},
			bindDateChange(e) {
				this.date = e.detail.value;

			},
			bindnickName(e) {
				this.nickName = e.detail.value;

			},
			bindnickMobile(e) {
				this.mobile = e.detail.value;

			},
			binddescription(e) {
				this.description = e.detail.value;

			},
			avatarChoose() {
				let that = this;
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album', 'camera'],
					success(res) {
						// tempFilePath可以作为img标签的src属性显示图片
						that.imgUpload(res.tempFilePaths);
						const tempFilePaths = res.tempFilePaths;
					}
				});
			},
			getUserInfo() {
				uni.getUserProfile({
					desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
					success: (res) => {
						console.log(res);
						uni.showToast({
							title: '已授权',
							icon: 'none',
							duration: 2000
						})
					}
				})
			},
			getphonenumber(e) {
				if (e.detail.iv) {
					console.log(e.detail.iv) //传后台解密换取手机号
					uni.showToast({
						title: '已授权',
						icon: 'none',
						duration: 2000
					})
				}
			},
			savaInfo() {
				let that = this;
				let nickname = that.nickName;
				let headimg = that.headimg;
				let gender = that.index + 1;
				let mobile = that.mobile;
				let region = that.region;
				let birthday = that.date;
				let description = that.description;
				let updata = {};
				if (!nickname) {
					uni.showToast({
						title: '请填写昵称',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				updata.nickname = nickname;
				if (!headimg) {
					headimg = that.avater;
				}
				updata.headimg = headimg;
				updata.gender = gender;
				if (that.isPoneAvailable(mobile)) {
					updata.mobile = mobile;
				} else {
					uni.showToast({
						title: '手机号码有误，请重填',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				if (!region) {
					uni.showToast({
						title: '请填写收货地址',
						icon: 'none',
						duration: 2000
					});
					return;
				} else {
					updata.region = region
				}
				if (birthday == "0000-00-00") {
					uni.showToast({
						title: '请选择生日',
						icon: 'none',
						duration: 2000
					});
					return;
				}
				updata.birthday = birthday;
				updata.description = description;
				that.updata(updata);
			},
			isPoneAvailable(poneInput) {
				var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
				if (!myreg.test(poneInput)) {
					return false;
				} else {
					return true;
				}
			},
			async updata(datas) {
				//传后台

			},
			imgUpload(file) {
				let that = this;
				uni.uploadFile({
					header: {
						Authorization: uni.getStorageSync('token')
					},
					url: '/api/upload/image', //需传后台图片上传接口
					filePath: file[0],
					name: 'file',
					formData: {
						type: 'user_headimg'
					},
					success: function(res) {
						var data = JSON.parse(res.data);
						data = data.data;
						that.avater = that.url + data.img;

						that.headimg = that.url + data.img;


					},
					fail: function(error) {
						console.log(error);
					}
				});
			},

		},
		onLoad() {}

	}
</script>

<style lang="scss" scoped>
	.container {
		display: block;
	}

	.ui-all {
		padding: 20rpx 40rpx;

		.avatar {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			.imgAvatar {
				width: 140rpx;
				height: 140rpx;
				border-radius: 50%;
				display: inline-block;
				vertical-align: middle;
				overflow: hidden;

				.iavatar {
					width: 100%;
					height: 100%;
					display: block;
				}
			}

			text {
				display: inline-block;
				vertical-align: middle;
				color: #8e8e93;
				font-size: 28rpx;
				margin-left: 40rpx;
			}

			&:after {
				content: ' ';
				width: 20rpx;
				height: 20rpx;
				border-top: solid 1px #030303;
				border-right: solid 1px #030303;
				transform: rotate(45deg);
				-ms-transform: rotate(45deg);
				/* IE 9 */
				-moz-transform: rotate(45deg);
				/* Firefox */
				-webkit-transform: rotate(45deg);
				/* Safari 和 Chrome */
				-o-transform: rotate(45deg);
				position: absolute;
				top: 85rpx;
				right: 0;
			}
		}

		.ui-list {
			width: 100%;
			text-align: left;
			padding: 20rpx 0;
			border-bottom: solid 1px #f2f2f2;
			position: relative;

			text {
				color: #4a4a4a;
				font-size: 28rpx;
				display: inline-block;
				vertical-align: middle;
				min-width: 150rpx;
			}

			input {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
			}

			button {
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				background: none;
				margin: 0;
				padding: 0;

				&::after {
					display: none;
				}
			}

			picker {
				width: 90%;
				color: #030303;
				font-size: 30rpx;
				display: inline-block;
				vertical-align: middle;
				position: absolute;
				top: 30rpx;
				left: 150rpx;
			}

			textarea {
				color: #030303;
				font-size: 30rpx;
				vertical-align: middle;
				height: 150rpx;
				width: 100%;
				margin-top: 50rpx;
			}

			.place {
				color: #999999;
				font-size: 28rpx;
			}
		}

		.right:after {
			content: ' ';
			width: 20rpx;
			height: 20rpx;
			border-top: solid 1px #030303;
			border-right: solid 1px #030303;
			transform: rotate(45deg);
			-ms-transform: rotate(45deg);
			/* IE 9 */
			-moz-transform: rotate(45deg);
			/* Firefox */
			-webkit-transform: rotate(45deg);
			/* Safari 和 Chrome */
			-o-transform: rotate(45deg);
			position: absolute;
			top: 40rpx;
			right: 0;
		}

		.save {
			background: #030303;
			border: none;
			color: #ffffff;
			margin-top: 40rpx;
			font-size: 28rpx;
		}
	}
</style>













<!-- <template>
	<scroll-view scroll-y="true" style="width: 100%;height: calc(100vh -  44px);">
		<view class="self__info" style="margin: 40rpx;">
			<view class="customer__avatar" style="width: 50px;height:50px;margin:40rpx auto;">
				<img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png"
					style="border-radius:50%; width: 100%;height: 100%;">
			</view>
			<uni-forms :modelValue="form" ref="form" :rules="rules" validate-trigger="bind">
				<uni-forms-item name="nickname" class="uni-form-item" label="昵称" required>
					<input class="uni-input" v-model="form.nickname" placeholder="请输入昵称" />
				</uni-forms-item>
				<uni-forms-item name="phone" class="uni-form-item" label="手机号" required>
					<input class="uni-input" v-model="form.phone" type="number" placeholder="请输入手机号" maxlength=11 />
				</uni-forms-item>
				<uni-forms-item name="email" class="uni-form-item" label="邮箱" required>
					<input class="uni-input" v-model="form.email" placeholder="请输入邮箱" />
				</uni-forms-item>
				<uni-forms-item name="password" class="uni-form-item" label="密码" required>
					<input class="uni-input" password type="text" v-model="form.password" placeholder="请输入密码" />
				</uni-forms-item>
				<uni-forms-item name="password" class="uni-form-item" label="性别">
					<select v-model='form.gender' class="uni-input">
						<option value="0">男</option>
						<option value="1">女</option>
					</select>
				</uni-forms-item>
				<uni-forms-item name="password" class="uni-form-item" label="生日">
					<select v-model='form.gender' class="uni-input">
						<option value="0">男</option>
						<option value="1">女</option>
					</select>
				</uni-forms-item>
			</uni-forms>
			<button @click="submit"
				style="margin-top: 100rpx;color:#fff;background-color: #333;letter-spacing: 8rpx;">保存修改</button>
		</view>
	</scroll-view>
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
			// 提交修改个人信息表单
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
</style>
 -->
