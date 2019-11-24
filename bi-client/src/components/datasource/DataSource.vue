<template>
  <div>
    <Table tableTitle="List of DataSource" />
    <md-button class="md-raised md-primary">{{ $t('New DataSource') }}</md-button>
  </div>
</template>

<style lang="scss" scoped>
  .md-button {
    text-transform: none;
  }
</style>

<script>
import Table from '../common/Table.vue'
export default {
  name: 'DataSource',
  components: {
    Table,
  },
}
</script>
