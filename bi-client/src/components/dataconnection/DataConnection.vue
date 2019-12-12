<template>
  <div>
    <loading
        :active.sync="isLoading"
        color="#448aff"
        :is-full-page="true"
      />

    <SelectedTable
      v-if="!isLoading"
      tableTitle="List of DataConnection"
      :tableData="getDataconnection"
      :itemDelete="deleteDataConnection"
      :onSelected="onSelected"
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
    <SnackBar :snackBarOptions="snackBarOptions" />
  </div>
</template>

<script>
import Loading from 'vue-loading-overlay';
import DialogEventBus from '@/event-bus/dialog'
import SelectedTable from '@/components/common/SelectedTable.vue'
import SnackBar from '@/components/common/SnackBar.vue'

import AddDataConnectionDialog from './Dialog/AddDataConnectionDialog.vue'

export default {
  name: 'DataConnection',
  components: {
    Loading,
    SelectedTable,
    AddDataConnectionDialog,
    SnackBar,
  },
  data: () => ({
    selected: [],
    snackBarOptions: {
      message: '',
      messageType: '',
      showSnackBar: false,
    }
  }),
  methods: {
    show(eventName) {
      DialogEventBus.$emit(eventName)
    },
    onSelected(e) {
      this.selected = e
    },
    async setDataconnection() {
      const { data } = await this.axios.get('/api/dataconnection')
      this.$store.dispatch('setDataconnection', data)
    },
    async setSupportedConnection() {
      const { data } = await this.axios.get('/api/dataconnection/supportedconnection')
      this.$store.dispatch('setSupportedConnection', data)
    },
    async deleteDataConnection() {
      const ids = this.selected.map(item => (item.id))
      await this.axios.delete(`/api/dataconnection/${ids}`)
      this.setDataconnection()
    },
  },
  mounted() {
    this.setDataconnection()
    this.setSupportedConnection()
  },
  computed: {
    getDataconnection() {
      return this.$store.getters.getDataconnection
    },
    isLoading() {
      return this.$store.getters.isDataconnectionLoading
    },
  },
}
</script>
