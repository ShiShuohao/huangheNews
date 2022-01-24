import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../layout/Home')
  },
  {
    path: '/adminLogin',
    name: 'AdminLogin',
    component: () => import('../layout/AdminLogin')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../layout/About')
  },
  {
    path: '/adminHome',
    name: 'AdminHome',
    component: () => import('../layout/AdminHome'),
    /*
        嵌套路由
        本项目中路由分成两层，第一层是App.vue中的router-view，根据第一层路由地址的不同，显示不同页面，这些页面都在layout文件夹里
        第二层路由是SuperAdminHome.vue和AdminHome.vue中的router-view，根据第二层路由的不同显示不同页面，这些页面都在views文件夹里
     */
    children: [
      {
        path: '',
        name: 'UserManage',
        component: () => import('../views/admin/UserManage')
      },
      {
        path: '/publishNews',
        name: 'PublishNews',
        component: () => import('../views/admin/PublishNews')
      },
      {
        path: '/publishLog',
        name: 'PublishLog',
        component: () => import('../views/admin/PublishLog')
      },
      {
        path: '/newsListForFirst',
        name: 'NewsListForFirst',
        component: () => import('../views/admin/NewsListForFirst')
      },
      {
        path: '/newsListForNotFirst',
        name: 'NewsListForNotFirst',
        component: () => import('../views/admin/NewsListForNotFirst')
      },
      {
        path: '/logList',
        name: 'LogList',
        component: () => import('../views/admin/LogList')
      },
      {
        path: '/freeType',
        name: 'FreeType',
        component: () => import('../views/admin/FreeType')
      },
      {
        path: '/fixedType',
        name: 'FixedType',
        component: () => import('../views/admin/FixedType')
      },
      {
        path: '/broadcastManage',
        name: 'BroadcastManage',
        component: () => import('../views/admin/BroadcastManage')
      }
    ]
  },
  {
    path: '/superAdminLogin',
    name: 'SuperAdminLogin',
    component: () => import('../layout/SuperAdminLogin')
  },
  {
    path: '/superAdminHome',
    name: 'SuperAdminHome',
    component: () => import('../layout/SuperAdminHome'),
    /*
        嵌套路由
        本项目中路由分成两层，第一层是App.vue中的router-view，根据第一层路由地址的不同，显示不同页面，这些页面都在layout文件夹里
        第二层路由是SuperAdminHome.vue和AdminHome.vue中的router-view，根据第二层路由的不同显示不同页面，这些页面都在views文件夹里
     */
    children: [
      {
        path: '',
        name: 'AdminList',
        component: () => import('../views/superAdmin/AdminList')
      },
      {
        path: '/adminRequest',
        name: 'AdminRequest',
        component: () => import('../views/superAdmin/AdminRequest')
      }
    ]
  },
  {
    path: '/searchResult',
    name: 'SearchResult',
    component: () => import('../layout/SearchResult')
  },
  {
    path: '/allType',
    name: 'AllType',
    component: () => import('../layout/AllType')
  },
  {
    path: '/finishUserInfo',
    name: 'FinishUserInfo',
    component: () => import('../layout/FinishUserInfo')
  },
  {
    path: '/firstNews',
    name: 'FirstNews',
    component: () => import('../layout/FirstNews')
  },
  {
    path: '/hotNews',
    name: 'HotNews',
    component: () => import('../layout/HotNews')
  },
  {
    path: '/latestNews',
    name: 'LatestNews',
    component: () => import('../layout/LatestNews')
  },
  {
    path: '/newsContent',
    name: 'NewsContent',
    component: () => import('../layout/NewsContent')
  },
  {
    path: '/typeContent',
    name: 'TypeContent',
    component: () => import('../layout/TypeContent')
  },
  {
    path: '/userHome',
    name: 'UserHome',
    component: () => import('../layout/UserHome'),
    children: [
      {
        path: '',
        name: 'SelfInfo',
        component: () => import('../views/user/SelfInfo')
      },
      {
        path: '/search',
        name: 'Search',
        component: () => import('../views/user/Search')
      },
      {
        path: '/myMessageOfReaded',
        name: 'MyMessageOfReaded',
        component: () => import('../views/user/MyMessageOfReaded')
      },
      {
        path: '/myMessageOfNotRead',
        name: 'MyMessageOfNotRead',
        component: () => import('../views/user/MyMessageOfNotRead')
      },
      {
        path: '/myFocus',
        name: 'MyFocus',
        component: () => import('../views/user/MyFocus')
      },
      {
        path: '/myComment',
        name: 'MyComment',
        component: () => import('../views/user/MyComment')
      },
      {
        path: '/myCollect',
        name: 'MyCollect',
        component: () => import('../views/user/MyCollect')
      },
      {
        path: '/requestToAdmin',
        name: 'RequestToAdmin',
        component: () => import('../views/user/RequestToAdmin')
      }
    ]
  },
  {
    path: '/userLogin',
    name: 'UserLogin',
    component: () => import('../layout/UserLogin')
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
