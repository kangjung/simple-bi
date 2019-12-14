<template>
  <grid
    :data="gridProps.data"
    :columns="gridProps.columns"
    :options="gridProps.options"
    theme="clean"
  />
</template>

<script>
  import 'tui-grid/dist/tui-grid.css'
  import { Grid } from '@toast-ui/vue-grid'

  export default {
    name: 'TableVisualization',
    components: {
      Grid,
    },
    created() {
      this.gridProps = {
        data: [ // for rowData prop
          {
            name: 'Beautiful Lies',
            artist: 'Birdy'
          },
          {
            name: 'X',
            artist: 'Ed Sheeran'
          }
        ],
        columns: [ // for columnData prop
          {
            header: 'Name',
            name: 'name',
          },
          {
            header: 'Artist',
            name: 'artist'
          }
        ]
      }
    }
  }
</script>
