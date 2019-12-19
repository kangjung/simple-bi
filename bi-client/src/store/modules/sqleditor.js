import Vue from 'vue'

export default {
  state: {
    tabs: [{name: 'untitled', run: false, result: []}],
    activeTabIndex: 0,
    metaData: {},
  },
  mutations: {
    mutateTabs(state, payload) {
      if (!Array.isArray(payload)) {
        state.tabs.push({
          ...payload,
          run: false,
          result: [],
        })
        state.activeTabIndex = state.tabs.length - 1
        return
      }
      state.tabs = payload;
    },
    mutateMetaData(state, payload) {
      state.metaData = payload
    },
    mutateActive(state, payload) {
      state.activeTabIndex = payload
    },
    mutateRun(state) {
      const currentTab = state.tabs[state.activeTabIndex]
      Vue.set(currentTab, 'run', !currentTab.run)
    },
    mutateResult(state, payload) {
      const currentTab = state.tabs[state.activeTabIndex]
      Vue.set(currentTab, 'run', false)
      Vue.set(currentTab, 'result', payload)
    },
  },
  actions: {
    addTab({ commit }, payload) {
      commit('mutateTabs', payload)
    },
    setTabs({ commit }, payload) {
      commit('mutateTabs', payload)
    },
    setActive({ commit }, payload) {
      commit('mutateActive', payload)
    },
    setMetaData({ commit }, payload) {
      commit('mutateMetaData', payload)
    },
    runQuery({ commit }) {
      commit('mutateRun')
    },
    setResult({ commit }, payload) {
      commit('mutateResult', payload)
    }
  },
  getters: {
    getActiveTabIndex: (state) => (state.activeTabIndex),
    getTabs: (state) => (state.tabs),
    getMetaData: (state) => (state.metaData),
    getResult: (state) => (state.tabs[state.activeTabIndex].result),
    isRunningQuery: (state) => (state.tabs[state.activeTabIndex].run)
  }
}
