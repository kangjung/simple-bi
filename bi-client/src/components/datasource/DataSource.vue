<template>
  <div>
    <Table
      tableTitle="List of DataSource"
      :tableData="getDatasource"
      defaultSortColumn="id"
      defaultSortOrder="desc"
    />

    <div class="align-right">
      <md-button class="md-raised md-primary" @click="show('show-new-data-source-dialog')">{{ $t('New DataSource') }}</md-button>
      <md-button class="md-raised md-primary" @click="show('show-csv-file-upload-dialog')">{{ $t('Import Csv Files') }}</md-button>
    </div>

    <NewDataSourceDialog />
    <CsvFileUploadDialog />
  </div>
</template>

<style lang="scss" scoped>
  .md-button {
    text-transform: none;
  }
  .align-right {
    text-align: right;
  }
</style>

<script>
import Table from '@/components/common/Table.vue'
import DialogEventBus from '@/event-bus/dialog'

import NewDataSourceDialog from './Dialog/NewDataSourceDialog.vue'
import CsvFileUploadDialog from './Dialog/CsvFileUploadDialog.vue'

import HttpCode from '@/constants/http.code'

export default {
  name: 'DataSource',
  data: () => ({
    showDialog: false
  }),
  methods: {
    show(dialogEventBus) {
      DialogEventBus.$emit(dialogEventBus)
    }
  },
  components: {
    Table,
    NewDataSourceDialog,
    CsvFileUploadDialog,
  },
  async mounted() {
    const { data, status } = await this.axios.get('/api/datasource')
    if (status !== HttpCode.HTTP_OK) {
      return;
    }
    this.$store.dispatch('setDatasource', data)
  },
  computed: {
    getDatasource() {
      return this.$store.getters.getDatasource
    }
  },
}
</script>
