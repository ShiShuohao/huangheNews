import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus' //引入Element-plus
import zhCn from 'element-plus/es/locale/lang/zh-cn'  //Element-plus国际化
import 'element-plus/dist/index.css'
// 导入全局样式表
import './assets/css/global/global.css'

//项目根节点
createApp(App).use(store).use(router).use(ElementPlus,{locale: zhCn,}).mount('#app')
