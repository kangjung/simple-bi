<template>
  <e-charts :options="option" />
</template>

<script>
  import ECharts from 'vue-echarts'
  import 'echarts/lib/chart/bar'
  import 'echarts/lib/component/tooltip'
  import 'echarts/lib/component/legend'
  import 'echarts/lib/component/toolbox'
  export default {
    name: 'BarChart',
    props: {
      xAxis: {
        type: Array,
      },
      yAxis: {
        type: Array,
      }
    },
    components: {
      ECharts,
    },
    data: function () {
      return {
        option: {
            title: {
              text: 'Bar Chart',
              subtext: 'Sub Text',
            },
            tooltip : {
              trigger: 'axis',
              axisPointer : {
                type : 'shadow'
              }
            },
            toolbox: {
              show: true,
              feature: {
                dataView : {
                  title: this.$t('DataView'),
                  show: true,
                  readOnly: false,
                  lang: [this.$t('DataView'), this.$t('Close'), this.$t('Save')]
                },
                saveAsImage : { title: this.$t('Download'), show: true}
              }
            },
            legend: {
              data: this.xAxis
            },
            xAxis: [
              {
                type: 'category',
                data: [1, 2],
              }
            ],
            yAxis: [
              {
                type: 'value',
              }
            ],
            series: [
              {
                name: 'kuckjwi',
                data: [100, 120],
                type: 'bar'
              },
              {
                name: 'mskim',
                data: [500, 120],
                type: 'bar'
              },
            ]
        }
      }
    }
  }
</script>