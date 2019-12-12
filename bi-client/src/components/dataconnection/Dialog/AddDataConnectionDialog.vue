<template>
  <div>
    <md-dialog
      :md-active.sync="showDialog"
      @md-closed="init"
      @md-opened="opened"
    >
      <md-dialog-title>{{ $t('New DataConnection') }}</md-dialog-title>
      <md-dialog-content>
        <md-field :class="getValidationClass('name')">
          <label>{{ $t('DataConnection Name') }}</label>
          <md-input ref="setupFocus" v-model="form.name" required />
          <span class="md-error" v-if="!$v.form.name.required">{{ $t('Enter DataConnection Name.') }}</span>
        </md-field>
        <md-field :class="getValidationClass('userName')">
          <label>{{ $t('Database User Name') }}</label>
          <md-input v-model="form.userName" required />
          <span class="md-error" v-if="!$v.form.userName.required">{{ $t('Enter Your Database User Name.') }}</span>
        </md-field>
        <md-field :class="getValidationClass('password')">
          <label>{{ $t('Database Password') }}</label>
          <md-input type="password" v-model="form.password" required />
          <span class="md-error" v-if="!$v.form.password.required">{{ $t('Enter Your Database Password.') }}</span>
        </md-field>
        <md-field :class="getValidationClass('url')">
          <label>{{ $t('Database Url') }}</label>
          <md-input v-model="form.url" required />
          <span class="md-error" v-if="!$v.form.url.required">{{ $t('Enter Your Database Url.') }}</span>
          <md-button class="md-primary" @click="testConnection()">{{ $t('Test Connection') }}</md-button>
        </md-field>
        <md-field>
          <md-select v-model="form.dbType">
            <md-option
              v-for="supportedConnection in getSupportedConnection"
              :key="supportedConnection"
              :value="supportedConnection.toLowerCase()"
            >
              {{supportedConnection}}
            </md-option>
          </md-select>
        </md-field>
        <div :class="getConnectionCheckClass()">
          <span>{{ $t(connectionCheck.message) }}</span>
        </div>
      </md-dialog-content>
      <md-dialog-actions>
        <md-button class="md-primary" @click="onApply">{{ $t('Create') }}</md-button>
        <md-button class="md-primary" @click="onClose">{{ $t('Close') }}</md-button>
      </md-dialog-actions>
    </md-dialog>
  </div>
</template>

<style lang="scss" scoped>
  .md-dialog {
    width: 90%;
    height: 100%;
  }
  .connection-check-true {
    color: #0000ff;
  }
  .connection-check-false {
    color: #ff0000;
  }
</style>

<script>
import DialogEventBus from '@/event-bus/dialog'
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'

export default {
  name: 'AddDataConnectionDialog',
  mixins: [validationMixin],
  data: () => ({
    showDialog: false,
    form: {
      name: '',
      userName: '',
      password: '',
      url: '',
      dbType: 'mysql',
    },
    connectionCheck : {}
  }),
  props: {
    dataConnection: {
      type: Object,
    }
  },
  validations: {
    form: {
      name: {
        required,
      },
      userName: {
        required,
      },
      password: {
        required,
      },
      url: {
        required,
      },
      dbType: {
        required,
      }
    }
  },
  methods: {
    init() {
      this.$v.$reset()
      this.form = {
        name: '',
        userName: '',
        password: '',
        url: '',
        dbType: 'mysql',
      }
      this.connectionCheck = {}
    },
    opened() {
      setTimeout(() => {this.$refs.setupFocus.$el.focus()}, 250)
    },
    validate() {
      this.$v.$touch()
      return !this.$v.$invalid
    },
    async onApply() {
      if (!this.validate()) {
        return 
      }

      const { data } = await this.axios.post('/api/dataconnection', JSON.stringify(this.form), {
        headers: {
          'Content-Type': 'application/json',
        },
      })

      this.$store.dispatch('addDataconnection', data)
      this.showDialog = false
    },
    onClose() {
      this.showDialog = false
    },
    getValidationClass(fieldName) {
      const field = this.$v.form[fieldName]
      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        }
      }
    },
    getConnectionCheckClass() {
      return `connection-check-${this.connectionCheck.connected}`
    },
    async testConnection() {
      if (!this.validate()) {
        return 
      }

      const { data } = await this.axios.post('/api/dataconnection/connection', JSON.stringify(this.form), {
        headers: {
          'Content-Type': 'application/json',
        },
      })

      this.connectionCheck = data

      this.$v.$touch()
    }
  },
  mounted() {
    DialogEventBus.$on('show-new-data-connection-dialog', () => {
      this.showDialog = true
    })
  },
  computed: {
    getSupportedConnection() {
      return this.$store.getters.getSupportedConnection
    },
  },
}
</script>
