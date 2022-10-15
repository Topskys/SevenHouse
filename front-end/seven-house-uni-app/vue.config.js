// 配置跨域
module.exports = {
	devServer: {
		port: '8080',
		// disableHostCheck: true,
		proxy: {
			'/api': {
				target: 'http://localhost:8080',
				changeOrigin: true,
				pathRewrite: {
					'^/api': ''
				}
			}
		}
	}
}
