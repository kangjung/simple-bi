<template>
  <div>
    <!-- TODO(kuckjwi): find theme. -->
    <MonacoEditor
      class="editor"
      v-model="code"
      theme="vs-dark"
      language="sql"
      :options="options"
      @editorDidMount="editorDidMount"
    />
    <md-button class="md-raised md-primary">
      {{$t('Run Query')}}
    </md-button>
  </div>
</template>

<script>
  import MonacoEditor from 'vue-monaco'
  export default {
    name: 'Editor',
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
      editorDidMount(editor) {
        editor.focus()
      }
    },
  }
</script>

<style lang="scss">
  .editor {
    height: 250px;
  }
</style>
