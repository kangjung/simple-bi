<template>
  <EditorTab />
</template>

<script>
import WebSocket from '@/websocket/websocket'
import EditorTab from './EditorTab.vue'

export default {
  name: 'SqlEditor',
  components: {
    EditorTab,
  },
  data: () => ({
    ws: null,
  }),
  created() {
    this.ws = new WebSocket('ws://localhost:8000/simple-bi/websocket', this.$route.params.dataconnectionId)
    this.ws.connect((message) => {
      console.log(this.$store)
      console.log(message)
    })
  },
}
</script>