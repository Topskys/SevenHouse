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
	initCategory: () => {
		return {
			code: 200,
			data: {
				msg: 'success',
				category: {
					notice: "公告：本店今日有上新，欢迎订购", // 在此作公告和修业提示
					categoryList: [{
							index: 0,
							title: "分类0",
						},
						{
							index: 1,
							title: "分类1",
						},
						{
							index: 2,
							title: "分类2",
						},
						{
							index: 3,
							title: "分类3",
						},
						{
							index: 4,
							title: "分类4",
						},
						{
							index: 5,
							title: "分类5",
						},
						{
							index: 6,
							title: "分类6",
						},
						{
							index: 7,
							title: "分类7",
						},
						{
							index: 8,
							title: "分类8",
						},
						{
							index: 9,
							title: "分类9",
						}, {
							index: 10,
							title: "分类10",
						}, {
							index: 11,
							title: "分类11",
						}, {
							index: 12,
							title: "分类12",
						}, {
							index: 13,
							title: "分类13",
						}, {
							index: 14,
							title: "分类14",
						},
					],
				}
			}
		}
	},
	productList: config => {
		// const {
		// 	offset = 1
		// } = param2Obj(config.url)
		const {
			offset = 0
		} = JSON.parse(config.body)
		let goodList = [{
				id: 0,
				orderId: '',
				discount: 0,
				status: "",
				price: 9,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			}, {
				id: 1,
				orderId: '',
				discount: 0,
				status: "",
				price: 3,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			}, {
				id: 2,
				orderId: '',
				discount: 0,
				status: "",
				price: 9,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			},
			{
				id: 3,
				orderId: '',
				discount: 0,
				status: "",
				price: 3,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			},
			{
				id: 4,
				orderId: '',
				discount: 0,
				status: "",
				price: 3,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			},
			{
				id: 5,
				orderId: '',
				discount: 0,
				status: "",
				price: 2,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			},
			{
				id: 6,
				orderId: '',
				discount: 0,
				status: "",
				price: 3,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			},
			{
				id: 7,
				orderId: '',
				discount: 0,
				status: "",
				price: 3,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			}, {
				id: 8,
				orderId: '',
				discount: 0,
				status: "",
				price: 9,
				payPrice: 0 || 3299,
				name: "贵州茅台酒套餐",
				time: '',
				save: false, // 0 || 1
				pay: false,
				imgUrl: "/static/images/banner1.png",
				delete: false,
				count: 0, // 订购数量
				payWay: "",
				stock: 100, //库存
				description: "简要描述",
				classification: {
					index: 0,
					title: "该商品所属的类别"
				}
			},
		]

		return {
			code: 200,
			data: {
				msg: 'success',
				// goodList: offset ? goodList[offset] : []
				goodList,
				offset,
				config
			}
		}
	}

}
