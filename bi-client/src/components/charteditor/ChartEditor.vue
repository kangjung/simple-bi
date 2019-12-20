<template>
  <div>
    <div class="flex">
      <md-card>
        <md-card-content>
          <h4>{{ $t('Chart Options') }}</h4>
          <md-field>
            <label for="columns">Group By</label>
            <md-select>
              <md-option :value="column" :key="column" v-for="column in getColumns">
                {{ column }}
              </md-option>
            </md-select>
          </md-field>
          <md-field>
            <label for="xaxis">{{ $t('X-Axis') }}</label>
            <md-select>
              <md-option :value="column" :key="column" v-for="column in getColumns">
                {{ column }}
              </md-option>
            </md-select>
          </md-field>
          <md-field>
            <label for="yaxis">{{ $t('Y-Axis') }}</label>
            <md-select>
              <md-option :value="column" :key="column" v-for="column in getColumns">
                {{ column }}
              </md-option>
            </md-select>
          </md-field>
          <md-button class="md-raised md-primary run-btn">{{ $t('Run') }}</md-button>
        </md-card-content>
      </md-card>
      <md-card class="chart">
        <md-card-content class="chart-content">
          <bar-chart
            v-if="!$_.isEmpty(getResult)"
            :xAxis="xAxis"
            :yAxis="yAxis"
            style="width: 100%" 
          />
          <md-empty-state
            v-else
            md-icon="bar_chart"
            :md-label="$t('Create your first Chart')"
          >
          </md-empty-state>
        </md-card-content>
      </md-card>
    </div>
    <div class="btn-area">
      <md-button class="md-raised md-primary dashboard-btn">{{ $t('Add to Dashboard') }}</md-button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
  .flex {
    display: flex;
    .chart {
      flex: 1;
      .chart-content {
        text-align: center;
      }
    }
    .run-btn {
      margin-left: -5px;
    }
  }
  .dashboard-btn {
    margin-left: 15px;
  }
  .btn-area {
    margin-top: 5px;
  }
</style>

<script>
  import BarChart from '@/components/visualization/BarChart.vue'
  export default {
    name: 'ChartEditor',
    components: {
      BarChart,
    },
    data() {
      return {
        xAxis: ['kuckjwi', 'mskim'],
        yAxis: [120, 500],
      }
    },
    computed: {
      getResult() {
        return this.$store.getters.getResult
      },
      getColumns() {
        return []
      }
    },
  }
</script>