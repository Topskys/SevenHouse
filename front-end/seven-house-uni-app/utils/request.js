const BASE_URL = 'http://localhost:8888'

export const http = (options) => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			success: (res) => {
				if (res.data.status !== 200) return uni.showToast({
					title: '获取数据失败'
				})
				resolve(res.data)
			},
			fail: (err) => {
				uni.showToast({
					title: '获取数据失败'
				})
				reject(err)
			}
		})
	})
}
