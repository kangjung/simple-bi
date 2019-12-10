<template>
  <div>
    <loading
        :active.sync="isLoading"
        color="#448aff"
        :is-full-page="true"
      />

      <Table
        v-if="!isLoading"
        tableTitle="List of DataConnection"
        :tableData="getDataconnection"
        defaultSortColumn="id"
        defaultSortOrder="desc"
      />

      <div class="align-right">
        <md-button
          class="md-raised md-primary"
          @click="show('show-new-data-connection-dialog')"
        >
          {{ $t('New DataConnection') }}
        </md-button>
      </div>

      <AddDataConnectionDialog />
    </div>
</template>

<script>
import DialogEventBus from '@/event-bus/dialog'
import Loading from 'vue-loading-overlay';
import Table from '@/components/common/Table.vue'

import AddDataConnectionDialog from './Dialog/AddDataConnectionDialog.vue'

import HttpCode from '@/constants/http.code'

export default {
  name: 'DataConnection',
  components: {
    Loading,
    Table,
    AddDataConnectionDialog,
  },
  methods: {
    show(eventName) {
      DialogEventBus.$emit(eventName)
    },
    async setDataconnection() {
      const { data, status } = await this.axios.get('/api/dataconnection')
      if (status !== HttpCode.HTTP_OK) {
        return;
      }
      this.$store.dispatch('setDataconnection', data)
    },
    async setSupportedConnection() {
      const { data, status } = await this.axios.get('/api/dataconnection/supportedconnection')
      if (status !== HttpCode.HTTP_OK) {
        return;
      }
      this.$store.dispatch('setSupportedConnection', data)
    }
  },
  mounted() {
    this.setDataconnection()
    this.setSupportedConnection()
  },
  computed: {
    getDataconnection() {
      return this.$store.getters.getDataconnection
    },
    isLoading () {
      return this.$store.getters.isDataconnectionLoading
    },
  },
}
</script>
