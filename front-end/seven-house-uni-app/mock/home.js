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
	initHomeData: () => {
		return {
			code: 200,
			data: {
				msg: 'success',
				banners: [{
						imgUrl: 'static/images/banner1.png',
						url: "",
					},
					{
						imgUrl: 'static/images/banner2.png',
						url: "",
					},
					{
						imgUrl: 'static/images/banner2.png',
						url: "",
					},
				],
				seat: [{
						id: 0,
						name: '您尚未选座？选座'
					},
					{
						id: 1,
						name: '#001 座'
					},
					{
						id: 2,
						name: '#002 座'
					},
					{
						id: 3,
						name: '#003 座'
					},
					{
						id: 4,
						name: '#004 座'
					},
					{
						id: 5,
						name: '#005 包间'
					},
				],
				news: [{
						imgUrl: 'static/images/news2.png',
						url: '',
					},
					{
						imgUrl: 'static/images/news1.png',
						url: '',
					},
					{
						imgUrl: 'static/images/news3.png',
						url: '',
					},
				]
			}
		}
	},
	setSeat: config => { // (req,res)
		const {
			username,
			seat
		} = JSON.parse(config.body)
		return {
			code: 200,
			data: {
				msg: 'success',
				config: config || "请求参数为空"
			}
		}
	}

}
