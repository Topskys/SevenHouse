// 后台接口
// const baseUrl = "http://localhost:8889"
// mock接口
const baseUrl = ""

const request = (options) => {
	return new Promise((resolve, reject) => {
		// uni.showLoading({
		// 	title: '加载中'
		// });
		uni.request({
			method: options.method || "get", // 请求类型建议小写与mock的一致
			url: baseUrl + options.url,
			data: options.data || {}, // data: JSON.stringify(options.data || {}), 
			headers: options.headers || { // 请求头需要加上，有的post请求，没有默认加上，mock里接收不到参数
				'Accept': 'application/json',
				'Content-Type': 'application/json',
				'Token': `${uni.getStorageSync("token")}`
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
