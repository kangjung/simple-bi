<template>
  <div>
    <md-dialog :md-active.sync="showDialog" @md-opened="opened" @md-closed="init">
      <md-dialog-title>{{ $t('New DataSource') }}</md-dialog-title>
      <md-dialog-content>
        <md-field :class="messageClass">
          <label>{{ $t('DataSource Name') }}</label>
          <md-input ref="focusable" v-model="dataSourceForm.name" required />
          <span class="md-error">{{ $t('Enter DataSource Name.') }}</span>
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
  export default {
    name: 'NewDataSourceDialog',
    data: () => ({
      showDialog: false,
      dataSourceForm: {
        name: ''
      }
    }),
    methods: {
      init() {
        this.dataSourceForm = {
          name: '',
        }
      },
      opened() {
        setTimeout(() => {this.$refs.focusable.$el.focus()}, 250)
      },
      isValid() {
        return this.dataSourceForm.name
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
    },
    computed: {
      messageClass () {
        return {
          'md-invalid': !this.isValid()
        }
      }
    },
    mounted() {
      DialogEventBus.$on('show-new-data-source-dialog', () => {
        this.showDialog = true
      })
    }
  }
</script>
