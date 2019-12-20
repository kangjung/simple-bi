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
      :tableFunctions="tableFunctions"
      defaultSortColumn="id"
      defaultSortOrder="desc"
    />

    <div class="align-right">
      <md-button
        class="md-raised md-primary create"
        @click="show('show-data-connection-dialog')"
      >
        {{ $t('Create') }}
      </md-button>
    </div>

    <data-connection-dialog />
    <snack-bar :snackBarOptions="snackBarOptions" />
  </div>
</template>

<style lang="scss" scoped>
  .create {
    margin-right: 15px;
  }
</style>

<script>
  import Loading from 'vue-loading-overlay';
  import DialogEventBus from '@/event-bus/dialog'
  import Table from '@/components/common/Table.vue'
  import SnackBar from '@/components/common/SnackBar.vue'

  import DataConnectionDialog from './Dialog/DataConnectionDialog.vue'

  export default {
    name: 'DataConnection',
    components: {
      Loading,
      Table,
      DataConnectionDialog,
      SnackBar,
    },
    data() {
      return {
        selected: [],
        snackBarOptions: {
          message: '',
          messageType: '',
          showSnackBar: false,
        },
        tableFunctions: [
          {
            clazz: 'md-raised md-primary',
            onClick: this.openSqlEditor,
            name: 'Open to SQL-Editor',
          },
          {
            clazz: 'md-raised md-primary',
            onClick: this.showModifyDialog,
            name: 'Modify',
          },
          {
            clazz: 'md-raised md-accent',
            onClick: this.deleteDataConnection,
            name: 'Delete',
          },
        ]
      }
    },
    methods: {
      show(eventName) {
        DialogEventBus.$emit(eventName)
      },
      showModifyDialog(id) {
        DialogEventBus.$emit('show-data-connection-dialog', id)
      },
      openSqlEditor(id) {
        const routerResolve = this.$router.resolve({name: 'sqleditor'})
        // #/sqleditor/${connectionId}
        window.open(`${routerResolve.href}${routerResolve.location.name}/${id}`, '_blank')
      },
      async setDataconnection() {
        const { data } = await this.axios.get('/api/dataconnection')
        this.$store.dispatch('setDataconnection', data)
      },
      async setSupportedConnection() {
        const { data } = await this.axios.get('/api/dataconnection/supportedconnection')
        this.$store.dispatch('setSupportedConnection', data)
      },
      async deleteDataConnection(id) {
        await this.axios.delete(`/api/dataconnection/${id}`)
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
