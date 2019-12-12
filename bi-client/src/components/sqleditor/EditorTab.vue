<template>
  <div>
    <md-tabs class="md-transparent">
      <md-tab
        :key="index"
        v-for="(tab, index) in getTabs"
        :md-label="tab.name"
      > 
        <md-card>
          <md-card-content>
            <Editor />
          </md-card-content>
        </md-card>
      </md-tab>
    </md-tabs>
    <md-speed-dial class="md-bottom-right">
      <md-speed-dial-target class="md-primary">
        <md-icon>sync</md-icon>
      </md-speed-dial-target>
      <md-speed-dial-content>
        <md-button class="md-icon-button">
          <md-icon>directions</md-icon>
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
  export default {
    name: 'EditorTab',
    components: {
      Editor,
    },
    computed: {
      getTabs() {
        return this.$store.getters.getTabs
      }
    },
  }
</script>

<style lang="scss" scoped>
  .md-icon-button {
    margin-top: 3px;
  }
</style>
