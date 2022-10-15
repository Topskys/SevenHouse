const baseUrl = "http://localhost:8080"

const request = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			method: options.methods || "GET",
			url: baseUrl + options.url,
			data: JSON.stringify(options.data || {}),
			header: options.header || {
				'content-type': 'application/json;charset=utf-8'
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
