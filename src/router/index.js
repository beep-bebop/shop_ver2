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
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '/users',
    component: Layout,
    redirect: '/users',
    name: 'Users',
    meta: {
      title: 'Users',
      icon: 'nested'
    },
    children: [
      {
        path: 'view',
        name: 'Form',
        component: () => import('@/views/users/list'),
        meta: { title: 'View', icon: 'form' }
      },
      {
        path: 'edit',
        component: () => import('@/views/users/edit'),
        name: 'Form',
        meta: { title: 'Edit', icon: 'form' }
      }
    ]
  },

  {
    path: '/orders',
    component: Layout,
    redirect: '/orders',
    name: 'Orders',
    meta: {
      title: 'Orders',
      icon: 'nested'
    },
    children: [
      {
        path: 'list',
        name: 'Form',
        component: () => import('@/views/orders/list'),
        meta: { title: 'list', icon: 'form' }
      },
      {
        path: 'edit',
        component: () => import('@/views/orders/edit'),
        name: 'Form',
        meta: { title: 'Edit', icon: 'form' }
      }
    ]
  },

  {
    path: '/commodity',
    component: Layout,
    name: 'Commodity',
    redirect: '/commodity/category',
    meta: { title: 'commodity', icon: 'example' },
    children: [{
      path: 'category',
      name: 'category',
      component: () => import('@/views/commodity/category'),
      meta: { title: 'category', icon: 'table' }
    },
      {
        path: 'product',
        name: 'Product',
        component: () => import('@/views/commodity/product'),
        meta: { title: 'Product', icon: 'table' }
      },
      {
        path: 'item',
        name: 'Item',
        component: () => import('@/views/commodity/item'),
        meta: { title: 'Item', icon: 'table' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
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
