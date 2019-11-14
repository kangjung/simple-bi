<template>
  <div class="md-toolbar-row">
    <md-button class="md-icon-button" @click="setVisible">
      <md-icon>menu</md-icon>
    </md-button>
    <span class="md-title">My Title</span>
  </div>
</template>

<script>
  export default {
    name: 'AppToolBar',
    props: {
      visible: Boolean
    },
    methods: {
      setVisible() {
        this.$emit('set-visible', !this.visible)
      }
    }
  }
</script>
