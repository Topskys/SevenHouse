// 后台接口
// const baseUrl = "http://10.22.170.174:8989"
const baseUrl = "http://106.14.40.97:8989"
// 本地接口
// const baseUrl = "http://127.0.0.1:8989"
// mock接口
// const baseUrl = ""

const request = (options) => {
	return new Promise((resolve, reject) => {
		// uni.showLoading({
		// 	title: '加载中'
		// });
		uni.request({
			method: options.method || "get", // 请求类型建议小写与mock的一致
			url: baseUrl + options.url,
			data: options.data || {}, // data: JSON.stringify(options.data || {}), 
			header: {
				// 请求头需要加上，有的post请求，没有默认加上，mock里接收不到参数
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'token': `${uni.getStorageSync("token")}`,
				'userId': `${uni.getStorageSync("userInfo")?JSON.parse(uni.getStorageSync("userInfo")||'{}').id:''}`,
			},
			dataType: 'json',
		}).then((response) => {
			setTimeout(function() {
				uni.hideLoading();
			}, 200);
			let [error, res] = response;
			resolve(res.data);
		}).catch(error => {
			let [err, res] = error;
			reject(err)
		})
	});
}


export default request
