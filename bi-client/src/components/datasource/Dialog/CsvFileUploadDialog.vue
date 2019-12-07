<template>
  <div>
    <md-dialog :md-active.sync="showDialog" @md-opened="opened" @md-closed="init">
      <md-dialog-title>{{ $t('Import Csv Files') }}</md-dialog-title>
      <md-dialog-content>
        <md-field :class="messageClass">
          <label>{{ $t('DataSource Name') }}</label>
          <md-input ref="focusable" v-model="dataSourceForm.name" required />
          <span class="md-error">{{ $t('Enter DataSource Name.') }}</span>
        </md-field>
        <md-field :class="messageClass">
          <label>{{ $t('Csv Files') }}</label>
          <md-file accept=".csv" @md-change="onFileChange" />
          <span class="md-error">{{ $t('Please choose a csv file.') }}</span>
          <md-button class="md-primary" @click="upload">{{ $t('Upload') }}</md-button>
        </md-field>
      </md-dialog-content>
      <md-dialog-actions>
        <md-button class="md-primary" @click="onApply">{{ $t('Apply') }}</md-button>
        <md-button class="md-primary" @click="onClose">{{ $t('Close') }}</md-button>
      </md-dialog-actions>
    </md-dialog>
  </div>
</template>

<script>
  import DialogEventBus from '@/event-bus/dialog'
  import { objectToFormData } from '@/utils/axios.util'
  export default {
    name: 'CsvFileUploadDialog',
    data: () => ({
      showDialog: false,
      dataSourceForm: {
        name: '',
        csvFile: undefined,
      }
    }),
    methods: {
      init() {
        this.dataSourceForm = {
          name: '',
          csvFile: undefined,
        }
      },
      opened() {
        setTimeout(() => {this.$refs.focusable.$el.focus()}, 250)
      },
      isValid() {
        return this.dataSourceForm.name && this.dataSourceForm.csvFile
      },
      upload() {
        if (!this.dataSourceForm.csvFile) {
          return; 
        }
        this.axios.post('/api/datasource/upload', objectToFormData(this.dataSourceForm), {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
      },
      onApply() {
        if (!this.isValid()) {
          return 
        }
        this.showDialog = false
      },
      onClose() {
        this.showDialog = false
      },
      onFileChange(files) {
        this.dataSourceForm.csvFile = files[0]
      },
    },
    computed: {
      messageClass () {
        return {
          'md-invalid': !this.isValid()
        }
      }
    },
    mounted() {
      DialogEventBus.$on('show-csv-file-upload-dialog', () => {
        this.showDialog = true
      })
    }
  }
</script>
