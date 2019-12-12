export default {
  state: {
    datasources: [],
    supportedConnctions: [],
    loading: true,
  },
  mutations: {
    mutateDatasource(state, payload) {
      state.datasources = payload;
      state.loading = false;
    },
    mutateSupportedConnection(state, payload) {
      state.supportedConnctions = payload;
    }
  },
  actions: {
    setDatasource({ commit }, payload) {
      commit('mutateDatasource', payload)
    },
    setSupportedConnection({ commit }, payload) {
      commit('mutateSupportedConnection', payload)
    }
  },
  getters: {
    getDatasource: (state) => {
      return state.datasources
    },
    getSupportedConnection: (state) => {
      return state.supportedConnctions
    },
    isLoading: (state) => {
      return state.loading
    },
  }
}
