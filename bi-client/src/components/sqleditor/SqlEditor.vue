<template>
  <div>
    <editor-tab />
    <loading-bar :loading="loading" />
    <web-socket-retry-dialog :onConfirm="wsConnect" />
  </div>
</template>

<script>
  import WebSocket from '@/websocket/websocket'
  import EditorTab from './EditorTab.vue'
  import LoadingBar from '@/components/common/Loading.vue'
  import WebSocketRetryDialog from './Dialog/WebSocketRetryDialog'

  import DialogEventBus from '@/event-bus/dialog'
  import WebSocketEventBus from '@/event-bus/websocket'

  export default {
    name: 'SqlEditor',
    components: {
      EditorTab,
      LoadingBar,
      WebSocketRetryDialog,
    },
    data: () => ({
      ws: null,
      loading: true,
    }),
    created() {
      this.wsConnect()
    },
    mounted() {
      WebSocketEventBus.$on('connected', (connected) => {
        this.loading = !connected
        if (!connected) {
          DialogEventBus.$emit('show-retry-dialog', {
            title: 'WebSocket connection has been lost.',
            content: 'Are you sure you want to try connecting again?',
          })
        }
      })
    },
    methods: {
      wsConnect() {
        if (this.ws) {
          this.ws.disconnect()
          this.ws = null
        }
        this.ws = new WebSocket(this.$route.params.dataconnectionId)
        this.ws.connect((frame) => {
          // init data source.
          const body = this.parseFrameBody(frame);
          if (body.messageType === "ERROR") {
            DialogEventBus.$emit('show-retry-dialog', {
              title: 'Failed to initialize data source.',
              content: body.message,
            })
          }
        })
      },
      onMessage(frame) {
        const body = this.parseFrameBody(frame)
        switch (body.messageType) {
          default:
            break;
        }
      },
      parseFrameBody(frame) {
        return JSON.parse(frame.body);
      }
    }
  }
</script>
