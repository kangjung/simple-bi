<template>
    <md-snackbar
      :class="snackBarOptions.messageType"
      :md-position="position"
      :md-duration="duration"
      :md-active.sync="snackBarOptions.showSnackBar"
      md-persistent
    >
      <span>{{ $t(snackBarOptions.message) }}</span>
    </md-snackbar>
</template>

<style lang="scss" scoped>
  .warning {
    background-color: #bb7000 !important;
  }
</style>

<script>
export default {
  name: 'SnackBar',
  props: {
    snackBarOptions: {
      type: Object,
      required: true,
    }
  },
  data: () => ({
    position: 'left',
    duration: 3500,
  })
}
</script>