import Stomp from 'webstomp-client'
import WebSocketEventBus from '@/event-bus/websocket'

// TODO(kuckjwi): Change to factory method pattern.
export default class WebSocket {
  constructor(dataConnectionId) {
    this.dataConnectionId = dataConnectionId
  }

  connect(messageCallback) {
    // TODO(kuckjwi): move to config.
    this.stompClient = Stomp.client('ws://localhost:8000/simple-bi/websocket')
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/sqleditor/${this.dataConnectionId}`, messageCallback)
      WebSocketEventBus.$emit('connected', true)
    }, () => {
      WebSocketEventBus.$emit('connected', false)
    })
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect()
      this.stompClient = null
    }
    WebSocketEventBus.$emit('connected', false)
  }

  subscribe(url, messageCallback) {
    this.stompClient.subscribe(url, messageCallback)
  }

  send(url, message) {
    this.stompClient.send(url, JSON.stringify(message), {})
  }
}
