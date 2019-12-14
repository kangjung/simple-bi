<template>
  <div>
    <md-table
      v-model="tableData"
      md-card
      md-fixed-header
      :md-sort="defaultSortColumn"
      :md-sort-order="defaultSortOrder"
    >
      <md-table-toolbar>
        <h1 class="md-title">{{ $t(tableTitle) }}</h1>
      </md-table-toolbar>
      <md-table-row
        slot="md-table-row"
        slot-scope="{ item }"
      >
        <md-table-cell
          :key=key
          v-for="key in Object.keys(item)" 
          :md-label="$t(key)"
          :md-sort-by="key"
        >
          {{ item[key] }}
        </md-table-cell>
        <md-table-cell>
          <md-button
            :key="tf.name"
            v-for="tf in tableFunctions"
            :class="`md-raised ${tf.clazz}`"
            @click="tf.onClick(item.id)"
          >
            {{ $t(tf.name) }}
          </md-button>
        </md-table-cell>
      </md-table-row>
    </md-table>
  </div>
</template>

<script>
  export default {
    name: 'Table',
    data: () => ({
      headers: []
    }),
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
      tableFunctions: {
        type: Array,
        required: true,
      }
    }
  }
</script>
