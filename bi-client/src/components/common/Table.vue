<template>
  <!-- TODO(kuckjwi): It's prototype now, Change Required. -->
  <div>
    <md-table
      v-model="users"
      md-sort="defaultSortColumn"
      md-sort-order="defaultSortOrder"
      md-card
      md-fixed-header
    >
      <md-table-toolbar>
        <h1 class="md-title">{{ $t(tableTitle) }}</h1>
      </md-table-toolbar>
      <md-table-row slot="md-table-row" slot-scope="{ item }">
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
    name: 'Table',
    props: {
      tableTitle: {
        type: String,
        required: true
      },
      tableData: {
        type: Object,
        required: true
      },
      defaultSortColumn: String,
      defaultSortOrder: String,
    },
    data: () => ({
      // dummy data. (deprecated)
      users: [
        {
          nmae: 'CSV test',
          type: "File",
          date: "2019/11/22 13:08:01",
        },
        {
          nmae: 'CSV test',
          type: "Data Source",
          date: "2019/11/22 13:08:02",
        },
      ]
    })
  }
</script>
