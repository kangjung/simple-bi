<template>
  <md-dialog-alert
    :md-active.sync="showDialog"
    :md-title="$t(title)"
    :md-content="$t(content)"
    :md-confirm-text="$t('Close')"
    @md-confirm="onConfirm" />
</template>

<script>
  import DialogEventBus from '@/event-bus/dialog'
  export default {
    name: 'ErrorDialog',
    data: () => ({
      showDialog: false,
      title: '',
      content: '',
    }),
    methods: {
      onConfirm() {
        this.showDialog = false
      },
    },
    mounted() {
      DialogEventBus.$on('show-error-dialog', (message) => {
        this.showDialog = true
        this.title = message.title
        this.content = message.content
      })
    },
  }
</script>
