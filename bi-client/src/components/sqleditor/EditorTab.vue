<template>
  <div>
    <md-tabs class="md-transparent" @md-changed="onTabChange">
      <md-tab
        :key="index"
        :md-label="tab.name"
        :id="`sql-editor-${index}`"
        v-for="(tab, index) in getTabs"
      >
        <md-card>
          <md-card-content>
            <div class="sql-editor">
              <div class="table-bar">
                <table-bar />
              </div>
              <div class="editor-bar">
                <editor :index="index" />
                <table-visualization v-if="!$_.isEmpty(getResult)" :result="getResult" />
              </div>
            </div>
          </md-card-content>
        </md-card>
      </md-tab>
    </md-tabs>
    <md-speed-dial class="md-top-right" md-direction="bottom">
      <md-speed-dial-target class="md-primary">
        <md-icon>menu</md-icon>
      </md-speed-dial-target>
      <md-speed-dial-content>
        <md-button class="md-icon-button" @click="addTab">
          <md-icon>add</md-icon>
        </md-button>
        <md-button class="md-icon-button">
          <md-icon>streetview</md-icon>
        </md-button>
      </md-speed-dial-content>
    </md-speed-dial>
  </div>
</template>

<script>
  import Editor from './Editor.vue'
  import TableBar from './TableBar.vue'
  import TableVisualization from '@/components/visualization/Table'
  export default {
    name: 'EditorTab',
    components: {
      TableBar,
      Editor,
      TableVisualization,
    },
    methods: {
      addTab() {
        this.$store.dispatch('addTab', {
          name: 'Untitled',
        })
      },
      onTabChange(id) {
        if (id) {
          this.$store.dispatch('setActive', Number(id.split('-')[2]))
        }
      }
    },
    updated() {
      // const index = this.$store.getters.getActiveTabIndex
    },
    computed: {
      getTabs() {
        return this.$store.getters.getTabs
      },
      getResult() {
        return this.$store.getters.getResult
      }
    },
  }
</script>

<style lang="scss" scoped>
  .sql-editor {
    display: flex;
    .table-bar {
      width: 10%;
      margin-right: 12pt;
    }
    .editor-bar {
      width: 100%;
    }
    .md-icon-button {
      margin-top: 3px;
    }
  }
  .md-top-right {
    margin-top: 50px;
  }
</style>
