<template>
  <div class="page-container">
    <md-app md-mode="reveal">
      <md-app-toolbar class="md-primary">
        <AppToolBar @set-visible="setVisible" :visible="menuVisible" />
      </md-app-toolbar>      

      <md-app-drawer :md-active.sync="menuVisible">
        <AppNavBar />
      </md-app-drawer>

      <md-app-content>
        <router-view />
      </md-app-content>
    </md-app>
  </div>
</template>

<style lang="scss">
  .md-reveal {
    min-height: 100vh;
  }

  .md-drawer {
    width: auto !important;
    max-width: calc(100vw - 125px);
  }

  .md-table-head {
    &.md-sortable {
      text-transform: capitalize;
    }
  }
</style>

<script>
import AppToolBar from './components/common/AppToolBar.vue'
import AppNavBar from './components/common/AppNavBar.vue'
export default {
  name: 'app',
  components: {
    AppToolBar,
    AppNavBar,
  },
  data: () => ({
    menuVisible: false
  }),
  watch: {
    $route() {
      this.menuVisible = false
    }
  },
  methods: {
    setVisible(visible) {
      this.menuVisible = visible
    }
  }
}
</script>
