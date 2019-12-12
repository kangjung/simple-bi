import Stomp from 'webstomp-client'

export default class WebSocket {
  constructor(websocketUrl, dataConnectionId) {
    this.websocketUrl = websocketUrl
    this.dataConnectionId = dataConnectionId
    this.connected = false
  }

  connect(callback) {
    this.stompClient = Stomp.client(this.websocketUrl)
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/sqleditor/${this.dataConnectionId}`, callback)
      this.connected = true
    })
  }

  disconnect() {
    if (this.stompClient) {
      this.stompClient.disconnect()
      this.stompClient = null;
    }
    this.connected = false
  }

  isConnected() {
    return this.connected
  }
}
