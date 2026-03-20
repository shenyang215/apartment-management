import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/apartment/list'
  },
  {
    path: '/apartment/list',
    name: 'ApartmentList',
    component: () => import('@/views/apartment/ApartmentList.vue'),
    meta: {
      breadcrumb: ['公寓管理', '公寓列表']
    }
  },
  {
    path: '/apartment/building',
    name: 'BuildingList',
    component: () => import('@/views/apartment/BuildingList.vue'),
    meta: {
      breadcrumb: ['公寓管理', '楼栋管理']
    }
  },
  {
    path: '/apartment/room',
    name: 'RoomList',
    component: () => import('@/views/apartment/RoomList.vue'),
    meta: {
      breadcrumb: ['公寓管理', '房间管理']
    }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
