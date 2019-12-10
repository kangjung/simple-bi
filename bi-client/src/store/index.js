import Vue from 'vue'
import Vuex from 'vuex'
import datasource from './modules/datasource'
import dataconnection from './modules/dataconnection'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    datasource,
    dataconnection,
  },
})
