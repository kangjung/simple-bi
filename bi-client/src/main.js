import Vue from 'vue'
import VueMaterial from 'vue-material'
import VueRouter from 'vue-router'
import VueI18n from 'vue-i18n';

import App from './App.vue'
import Dashboard from './components/dashboard/Dashboard.vue'
import DataSource from './components/datasource/DataSource.vue'
import DataConnection from './components/dataconnection/DataConnection.vue'

import kr from './i18n/kr-message'

import 'flag-icon-css/css/flag-icon.min.css'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

Vue.config.productionTip = false

Vue.use(VueI18n)
Vue.use(VueRouter)
Vue.use(VueMaterial)

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
]

const router = new VueRouter({
  routes
})

new Vue({
  i18n,
  router,
  render: h => h(App)
}).$mount('#app')
