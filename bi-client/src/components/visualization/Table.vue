<template>
  <grid
    :data="getGridOption.data"
    :columns="getGridOption.columns"
    theme="clean"
  />
</template>

<script>
  import 'tui-grid/dist/tui-grid.css'
  import { Grid } from '@toast-ui/vue-grid'

  export default {
    name: 'TableVisualization',
    props: {
      result: {
        type: Array,
        required: true,
      }
    },
    components: {
      Grid,
    },
    computed: {
      getGridOption() {
        return {
          columns: Object.keys(this.result[0]).map(key => ({ header: key, name: key })),
          data: this.result
        }
      }
    },
  }
</script>
