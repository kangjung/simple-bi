// import Vue from 'vue'

export default {
  state: {
    dataconnections: [],
    loading: true,
  },
  mutations: {
    mutateDataconnection(state, payload) {
      // add
      if (!Array.isArray(payload)) {
        state.dataconnections.push(payload)  
        return;
      }
      state.dataconnections = payload;
      state.loading = false;
    },
  },
  actions: {
    addDataconnection({ commit }, payload) {
      commit('mutateDataconnection', payload)
    },
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
