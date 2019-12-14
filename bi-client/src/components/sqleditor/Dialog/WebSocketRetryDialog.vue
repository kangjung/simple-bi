<template>
  <div>
    <md-dialog-confirm
      :md-active.sync="showDialog"
      :md-title="$t(title)"
      :md-content="$t(content)"
      :md-confirm-text="$t('Retry')"
      :md-cancel-text="$t('Close Sql Editor')"
      @md-cancel="onCancel"
      @md-confirm="onConfirm" />
  </div>
</template>

<script>
  import DialogEventBus from '@/event-bus/dialog'
  export default {
    name: 'WebSocketRetryDialog',
    props: {
      onConfirm: {
        type: Function,
        required: true,
      },
    },
    data: () => ({
      showDialog: false,
      title: '',
      content: '',
    }),
    methods: {
      onCancel() {
        window.close()
      },
    },
    mounted() {
      DialogEventBus.$on('show-retry-dialog', (message) => {
        this.showDialog = true
        this.title = message.title
        this.content = message.content
      })
    },
  }
</script>
