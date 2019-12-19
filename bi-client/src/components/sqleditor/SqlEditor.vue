<template>
  <div>
    <editor-tab />
    <loading-bar :loading="loading" />
    <snack-bar :snackBarOptions="snackBarOptions" />
    <web-socket-retry-dialog :onConfirm="wsConnect" />
    <error-dialog />
  </div>
</template>

<script>
  import WebSocket from '@/websocket/websocket'
  import message from '@/websocket/message'
  import EditorTab from './EditorTab.vue'
  import LoadingBar from '@/components/common/Loading.vue'
  import SnackBar from '@/components/common/SnackBar.vue'
  import WebSocketRetryDialog from './Dialog/WebSocketRetryDialog'
  import ErrorDialog from './Dialog/ErrorDialog'

  import SnackBarEventBus from '@/event-bus/snackbar'
  import DialogEventBus from '@/event-bus/dialog'
  import WebSocketEventBus from '@/event-bus/websocket'

  export default {
    name: 'SqlEditor',
    components: {
      EditorTab,
      LoadingBar,
      SnackBar,
      WebSocketRetryDialog,
      ErrorDialog,
    },
    data: () => ({
      ws: null,
      loading: true,
      snackBarOptions: {
        message: '',
        messageType: '',
        showSnackBar: false,
      },
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
      WebSocketEventBus.$on('send', (message) => {
        this.ws.send(`/sqleditor/1/${this.$route.params.dataconnectionId}`, message)
      })
      SnackBarEventBus.$on('show', (options) => {
        this.snackBarOptions = options
      })
    },
    methods: {
      wsConnect() {
        this.ws = new WebSocket(this.$route.params.dataconnectionId)
        this.ws.connect((frame) => {
          // init data source.
          const body = this.parseFrameBody(frame);
          if (body.messageType === message.INIT_ERROR) {
            DialogEventBus.$emit('show-retry-dialog', {
              title: 'Failed to initialize data source.',
              content: body.message,
            })
            return;
          }
          // private websocket subscription.
          // TODO(kuckjwi): Modifying when login feature is complete.
          this.ws.subscribe(`/sqleditor/1/${this.$route.params.dataconnectionId}`, this.onMessage)
        })
      },
      onMessage(frame) {
        const body = this.parseFrameBody(frame)
        switch (body.messageType) {
          case message.META:
            this.$store.dispatch('setMetaData', body.data)
            break
          case message.QUERY_RESULT:
            this.$store.dispatch('setResult', body.data)
            break
          case message.ERROR:
            this.$store.dispatch('runQuery')
            DialogEventBus.$emit('show-error-dialog', {
              title: 'Oops!',
              content: body.message,
            })
            break
          default:
            break
        }
      },
      parseFrameBody(frame) {
        return JSON.parse(frame.body);
      }
    }
  }
</script>
