import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
const CustomerList = () => import('@/views/customer/index')
const OrderList = () => import('@/views/order/index')
const CategoryList = () => import('@/views/category/list.vue')
const ProductList = () => import('@/views/product/index.vue')
const DeskList = () => import('@/views/desk/list.vue')
const SaveList = () => import('@/views/save/index.vue')
const RecordList = () => import('@/views/save/record.vue')


export const constantRoutes = [{
    path: '/login',
    component: () =>
        import('@/views/login/index'),
    hidden: true
},

{
    path: '/404',
    component: () =>
        import('@/views/404'),
    hidden: true
},

{
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
        path: 'dashboard',
        name: 'Dashboard',
        component: () =>
            import('@/views/dashboard/index'),
        meta: { title: '首页', icon: 'dashboard' }
    }]
},
{
    path: '/product',
    component: Layout,
    name: 'Product',
    meta: { title: '商品管理', icon: 'el-icon-goods' },
    children: [{
        path: 'list',
        name: 'ProductList',
        component: ProductList,
        meta: { title: '商品列表' }
    },
    ]
},
{
    path: '/category',
    component: Layout,
    name: 'Category',
    meta: { title: '分类管理', icon: 'el-icon-menu' },
    children: [
        {
            path: 'list',
            name: 'categoryList',
            component: CategoryList,
            meta: { title: '分类列表' }
        }]
},
{
    path: '/order',
    component: Layout,
    name: 'Order',
    meta: { title: '订单管理', icon: 'el-icon-s-order' },
    children: [{
        path: 'list',
        name: 'orderList',
        component: OrderList,
        meta: { title: '订单列表' }
    }]
},
{
    path: '/save',
    component: Layout,
    name: 'Save',
    meta: { title: '寄存管理', icon: 'el-icon-coin' },
    children: [
        {
            path: 'list',
            name: 'saveList',
            component: SaveList,
            meta: { title: '存酒列表' }
        },
        {
            path: 'record',
            name: 'recordList',
            component: RecordList,
            meta: { title: '取酒记录' }
        }
    ]
},
{
    path: '/customer',
    component: Layout,
    name: 'Customer',
    meta: { title: '顾客管理', icon: 'el-icon-user' },
    children: [
        {
            path: 'list',
            name: 'customerList',
            component: CustomerList,
            meta: { title: '顾客列表' }
        },
    ]
},
{
    path: '/desk',
    component: Layout,
    name: 'Desk',
    meta: { title: '座位管理', icon: 'el-icon-dish' },
    children: [{
        path: 'list',
        name: 'deskList',
        component: DeskList,
        meta: { title: '座号列表' }
    }]
},


// 404 page must be placed at the end !!!
{ path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router