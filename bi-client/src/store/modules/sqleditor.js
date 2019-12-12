export default {
  state: {
    tabs: [{name: 'untitled'}],
    actvieTab: {},
  },
  mutations: {
    mutateTabs(state, payload) {
      state.tabs = payload;
    },
  },
  actions: {
    setTabs({ commit }, payload) {
      commit('mutateTabs', payload)
    },
  },
  getters: {
    getTabs: (state) => {
      return state.tabs
    },
  }
}
