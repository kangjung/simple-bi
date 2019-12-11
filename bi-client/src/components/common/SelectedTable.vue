<template>
  <div>
    <md-table
      v-model="tableData"
      md-card
      md-fixed-header
      :md-sort="defaultSortColumn"
      :md-sort-order="defaultSortOrder"
      @md-selected="onSelected"
    >
      <!-- TODO(kuckjwi): need to check the slot-scope -->
      <md-table-toolbar>
        <h1 class="md-title">{{ $t(tableTitle) }}</h1>
      </md-table-toolbar>
      <md-table-toolbar slot="md-table-alternate-header" slot-scope="{ count }">
        <div class="md-toolbar-section-start">{{ getAlternateLabel(count) }}</div>
        <div class="md-toolbar-section-end">
          <md-button class="md-icon-button" @click="itemDelete()">
            <md-icon>delete</md-icon>
          </md-button>
        </div>
      </md-table-toolbar>
      <md-table-row
        slot="md-table-row"
        slot-scope="{ item }"
        md-selectable="multiple"
        md-auto-select
      >
        <md-table-cell
          :key=key
          v-for="(key) in Object.keys(item)" 
          :md-label="$t(key)"
          :md-sort-by="key"
        >
          {{ item[key] }}
        </md-table-cell>
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
  export default {
    name: 'SelectedTable',
    props: {
      tableTitle: {
        type: String,
        required: true
      },
      tableData: {
        type: Array,
        required: true
      },
      defaultSortColumn: {
        type: String,
        required: true,
      },
      defaultSortOrder: {
        type: String,
        required: true,
      },
      onSelected: {
        type: Function,
        required: true,
      },
      itemDelete: {
        type: Function,
        required: true,
      },
    },
    methods: {
      getAlternateLabel (count) {
        return `${count}${this.$t('item selected')}`
      }
    }
  }
</script>
