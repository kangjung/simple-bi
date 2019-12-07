import Vue from 'vue'

export default {
  state: {
    datasource: [],
  },
  mutations: {
    mutateDatasource(state, payload) {
      Vue.set(state, 'datasource', payload)
    }
  },
  actions: {
    setDatasource({ commit }, payload) {
      commit('mutateDatasource', payload)
    }
  },
  getters: {
    getDatasource: (state) => {
      return state.datasource
    },
  }
}
