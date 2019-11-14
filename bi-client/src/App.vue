<template>
  <div class="container">
    <md-app md-mode="reveal">
      <md-app-toolbar class="md-primary">
        <AppToolBar @set-visible="setVisible" :visible="menuVisible" />
      </md-app-toolbar>

      <md-app-drawer :md-active.sync="menuVisible">
        <AppNavBar />
      </md-app-drawer>

      <md-app-content>
        <Dashboard />
      </md-app-content>
    </md-app>
  </div>
</template>

<style lang="scss" scoped>
  .md-reveal {
    min-height: 100vh;
  }
  .md-drawer {
    width: 230px;
    max-width: calc(100vw - 125px);
  }
</style>

<script>
import AppToolBar from './components/common/AppToolBar.vue'
import AppNavBar from './components/common/AppNavBar.vue'
import Dashboard from './components/Dashboard.vue'
export default {
  name: 'app',
  components: {
    AppToolBar,
    AppNavBar,
    Dashboard,
  },
  data: () => ({
    menuVisible: false
  }),
  methods: {
    setVisible(visible) {
      this.menuVisible = visible
    }
  }
}
</script>
