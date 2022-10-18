// 引入mockjs
const Mock = require('mockjs')
import category from './category'
import home from './home'
import user from './user'
import order from './order'


// home
Mock.mock('/api/home/list', home.initHomeData())
// type:请求类型必须小写
// callback：不要带括号
Mock.mock("/api/home/setSeat", 'post', home.setSeat)

// user
Mock.mock("/api/user/login", 'post', user.login)
Mock.mock("/api/user/register", 'post', user.register)
Mock.mock("/api/saveWine/list", 'get', user.saveWineList)
Mock.mock("/api/saveWine/take", 'post', user.takeSaveWine)
Mock.mock("/api/discount/take", 'post', user.takeDiscount)
Mock.mock("/api/userInfo/update", 'post', user.updateSelfInfo)
Mock.mock("/api/userInfo", 'get', user.getSelfInfo)


// order
Mock.mock("/api/order/list", 'get', order.initOrder)
Mock.mock("/api/order/del", 'post', order.delOrder)
Mock.mock("/api/order/pay", 'post', order.payOrder)
Mock.mock("/api/order/update", 'post', order.updateOrder)

// Mock.mock(/user\/edit/, 'post', user.updateUser)
// Mock.mock(/\/user\/getUser/, 'get', user.getUserList)
// Mock.mock(/\/user\/del/, 'get', user.deleteUser)


// category
Mock.mock('/api/category/list', category.initCategory)
Mock.mock('/api/category/product', 'post', category.productList)
