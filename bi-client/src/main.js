import Vue from 'vue'
import VueMaterial from 'vue-material'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n'

import App from '@/App.vue'
import Dashboard from '@/components/dashboard/Dashboard.vue'
import DataSource from '@/components/datasource/DataSource.vue'
import DataConnection from '@/components/dataconnection/DataConnection.vue'
import SqlEditor from '@/components/sqleditor/SqlEditor.vue'

import kr from '@/i18n/kr-message'
import axios from 'axios'
import VueAxios from 'vue-axios'
import underscore from 'vue-underscore'

import store from '@/store'

import 'flag-icon-css/css/flag-icon.min.css'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import 'vue-loading-overlay/dist/vue-loading.css'

Vue.config.productionTip = false

Vue.use(VueI18n)
Vue.use(VueRouter)
Vue.use(VueMaterial)
Vue.use(VueAxios, axios)
Vue.use(underscore)

const i18n = new VueI18n({
  locale: 'kr', // set locale
  fallbackLocale: 'en',
  messages: {
    kr: kr
  },
})

const routes = [
  { path: '/', component: Dashboard },
  { path: '/datasource', component: DataSource },
  { path: '/dataconnection', component: DataConnection },
  { path: '/sqleditor/:dataconnectionId', component: SqlEditor },
]

const router = new VueRouter({
  routes
})

new Vue({
  i18n,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
