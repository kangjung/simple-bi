import Vue from 'vue'

export default {
  state: {
    dataconnections: [],
    loading: true,
  },
  mutations: {
    mutateDataconnection(state, payload) {
      if (!Array.isArray(payload)) {
        const idx = state.dataconnections.findIndex(d => (d.id === payload.id))
        if (idx !== -1) {
          Vue.set(state.dataconnections, idx, payload)
        } else {
          state.dataconnections.push(payload)  
        }
        return;
      }
      state.dataconnections = payload;
      state.loading = false;
    },
  },
  actions: {
    setDataconnection({ commit }, payload) {
      commit('mutateDataconnection', payload)
    }
  },
  getters: {
    getDataconnection: (state) => {
      return state.dataconnections
    },
    isDataconnectionLoading: (state) => {
      return state.loading
    },
  }
}
