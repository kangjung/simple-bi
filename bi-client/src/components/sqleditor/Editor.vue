<template>
  <div>
    <!-- TODO(kuckjwi): find theme. -->
    <monaco-editor
      class="editor"
      v-model="code"
      theme="vs-dark"
      language="sql"
      ref="editor"
      :options="options"
    />
    <md-button v-show="!isRunningQuery" class="md-raised md-primary query" @click="runQuery()">
      {{ $t('Run Query') }}
    </md-button>
    <md-button v-show="isRunningQuery" class="md-raised md-accent" @click="stopQuery()">
      {{ $t('Stop Query') }}
    </md-button>
    <md-button class="md-raised md-primary">
      {{ $t('Create Chart') }}
    </md-button>
  </div>
</template>

<script>
  import MonacoEditor from 'vue-monaco'
  import message from '@/websocket/message'
  import WebSocketEventBus from '@/event-bus/websocket'
  import SnackBarEventBus from '@/event-bus/snackbar'
  export default {
    name: 'Editor',
    props: {
      index: {
        type: Number,
        required: true,
      },
    },
    data: () => ({
      code: '',
      options: {
        automaticLayout: true,
      }
    }),
    components: {
      MonacoEditor,
    },
    methods: {
      runQuery() {
        if (!this.code) {
          this.$refs.editor.getEditor().focus()
          SnackBarEventBus.$emit('show', {
            message: this.$t('Please enter the code.'),
            messageType: 'warning',
            showSnackBar: true,
          })
          return
        }
        this.$store.dispatch('runQuery')
        WebSocketEventBus.$emit('send', {
          messageType: message.RUN_QUERY,
          data: this.code,
        })
      },
      stopQuery() {
        // TODO(kuckjwi): stop query.
      }
    },
    computed: {
      getMetaData() {
        return this.$store.getters.getMetaData
      },
      isRunningQuery() {
        return this.$store.getters.isRunningQuery
      }
    }
  }
</script>

<style lang="scss" scoped>
  .query {
    margin-left: -1px;
  }
  .editor {
    height: 250px;
  }
</style>
