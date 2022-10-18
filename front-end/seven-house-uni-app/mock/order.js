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


export default {

	initOrder: config => {
		return {
			code: 200,
			data: {
				msg: 'success',
				list: [{
						id: 0,
						orderId: 'XS324564567438095',
						discount: 0,
						status: "待付款",
						price: 9,
						payPrice: 0 || 3299,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: false, // 0 || 1
						pay: false,
						imgUrl: "/static/images/banner1.png",
						delete: false,
						count: 1, // 订购数量
						payWay: "",
						stock: 0, //库存
						description: "简要描述",
						classification: {
							index: 0,
							title: "该商品所属的类别"
						}
					},
					{
						id: 2,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: false, // 0 || 1
						imgUrl: "/static/images/banner1.png",
						pay: false,
						orderId: 'XS324564247438095',
						discount: 0,
						status: "待付款",
						price: 3,
						payPrice: 0 || 3299,
						count: 1,
						delete: false,
						payWay: "",
						stock: 0, //库存
						description: "简要描述",
						classification: {
							index: 0,
							title: "该商品所属的类别"
						}
					},
					{
						id: 3,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: false, // 0 || 1
						imgUrl: "/static/images/banner1.png",
						pay: false,
						orderId: 'XS32456456824',
						discount: 0,
						status: "待付款",
						price: 1,
						payPrice: 0 || 3299,
						delete: false,
						count: 1,
						payWay: "",
						stock: 0, //库存
						description: "简要描述",
						classification: {
							index: 0,
							title: "该商品所属的类别"
						}
					},
					{
						id: 4,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: false, // 0 || 1
						imgUrl: "/static/images/banner1.png",
						pay: false,
						orderId: 'XS324564568247',
						discount: 0,
						status: "待付款",
						price: 2,
						payPrice: 0 || 3299,
						delete: false,
						count: 1,
						payWay: "",
						stock: 0, //库存
						description: "简要描述",
						classification: {
							index: 0,
							title: "该商品所属的类别"
						}
					},
					{
						id: 5,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: false, // 0 || 1
						imgUrl: "/static/images/banner1.png",
						pay: true,
						orderId: 'XS32456456824743',
						discount: 0,
						status: "交易成功",
						price: 8,
						payPrice: 0 || 3299,
						delete: false,
						count: 1,
						payWay: "",
						stock: 0, //库存
						description: "简要描述",
						classification: {
							index: 0,
							title: "该商品所属的类别"
						}
					},
					{
						id: 6,
						name: "贵州茅台酒套餐",
						time: new Date() || '2022-10-17',
						save: false, // 0 || 1
						imgUrl: "/static/images/banner1.png",
						pay: true,
						orderId: 'XS32456456824743',
						discount: 0,
						status: "交易成功",
						price: 3,
						payPrice: 0 || 3299,
						delete: false,
						count: 1,
						payWay: "",
						stock: 0, //库存
						description: "简要描述",
						classification: {
							index: 0,
							title: "该商品所属的类别"
						}
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
	delOrder: config => {
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
	payOrder: config => {
		const order = JSON.parse(config.body)
		if (!order) {
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
					config,
					payToken: new Date().getTime()
				}
			}
		}
	},
	updateOrder: config => {
		const order = JSON.parse(config.body)
		if (!order) {
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
					config,
				}
			}
		}
	}
}
