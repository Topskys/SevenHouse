import Mock from 'mockjs'


function param2Obj(url) {
	const search = url.split('?')[1]
	if (!search) {
		return {}
	}
	return JSON.parse(
		'{"' +
		decodeURIComponent(search)
		.replace(/"/g, '\\"')
		.replace(/&/g, '","')
		.replace(/=/g, '":"') +
		'"}'
	)
}

var registerList = []

var userInfo = {
	nickname: "顾长风",
	id: '000001', // ID
	class: 'LV1', // 用户级别
	discount: '0', // 优惠劵
	score: "8080", // 积分
	avatarUrl: "/static/images/girl.jpg",
	gender: 0, // 0 or 1
	address: '',
	phone: 18790802012,
	birthday: '', // 2022-10-13
	sign: '', // 签名
}

export default {
	login: config => {
		console.log(config)
		const {
			email,
			password
		} = JSON.parse(config.body)
		if (email == "123456@qq.com" && password == "12345678") {
			return {
				code: 200,
				data: {
					msg: 'success',
					token: Mock.mock({
						'string': '@word(20,30)'
					}),
					userInfo,
				}
			}
		} else {
			return {
				code: 400,
				data: {
					msg: 'error',
				}
			}
		}
		// if (userRegister.forEach(item => {
		// 		if (item.email == email && item.password == password) {
		// 			return true
		// 		} else {
		// 			return false
		// 		}
		// 	})) {
		// 	return {
		// 		code: 200,
		// 		data: {
		// 			msg: 'success',
		// 			token: Mock.mock({
		// 				'string': '@word(20,30)'
		// 			})
		// 		}
		// 	}
		// } else {
		// 	return {
		// 		code: 400,
		// 		data: {
		// 			msg: 'error',
		// 		}
		// 	}
		// }
	},
	register: config => { // (req,res)
		const {
			email,
			phone,
			password
		} = JSON.parse(config.body)
		if (email && password && password) {
			userRegister.push({
				email,
				phone,
				password
			})
			return {
				code: 200,
				data: {
					msg: 'success',
					id: Mock.mock({
						id: '@id()'
					})
				}
			}
		} else {
			return {
				code: 200,
				data: {
					msg: 'error',
				}
			}
		}
	},
	saveWineList: config => {
		return {
			code: 200,
			data: {
				msg: 'success',
				list: [{
						id: 1,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: 1, // 0 || 1
						imgUrl: "/static/images/banner1.png",
					},
					{
						id: 2,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: 1, // 0 || 1
						imgUrl: "/static/images/banner1.png",
					},
					{
						id: 3,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: 1, // 0 || 1
						imgUrl: "/static/images/banner1.png",
					},
					{
						id: 4,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: 1, // 0 || 1
						imgUrl: "/static/images/banner1.png",
					},
					{
						id: 5,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: 1, // 0 || 1
						imgUrl: "/static/images/banner1.png",
					},
					{
						id: 6,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: 1, // 0 || 1
						imgUrl: "/static/images/banner1.png",
					}
				]
			}
		}
	},
	takeSaveWine: config => {
		const item = JSON.parse(config.body)
		if (!item) {
			return {
				code: 400,
				data: {
					msg: 'error',
					config
				}
			}
		} else {
			return {
				code: 200,
				data: {
					msg: 'success',
					config
				}
			}
		}
	},
	takeDiscount: config => {
		const item = JSON.parse(config.body)
		if (!item) {
			return {
				code: 400,
				data: {
					msg: 'error',
					config
				}
			}
		} else {
			return {
				code: 200,
				data: {
					msg: 'success',
					config
				}
			}
		}
	},
	updateSelfInfo: config => {
		const info = JSON.parse(config.body)
		if (info) {
			userInfo.avatarUrl = info.avatarUrl
			userInfo.nickname = info.nickname
			userInfo.phone = info.phone
			userInfo.gender = info.gender
			userInfo.address = info.address
			userInfo.birthday = info.birthday
			userInfo.sign = info.sign
			return {
				code: 200,
				data: {
					userInfo,
					msg: 'success',
				}
			}
		} else {
			return {
				code: 400,
				data: {
					msg: 'error',
					config,
				}
			}
		}
	},
	getSelfInfo: config => {
		return {
			code: 200,
			data: {
				msg: 'success',
				userInfo,
				config
			}
		}
	}
}
