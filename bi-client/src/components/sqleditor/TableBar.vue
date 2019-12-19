<template>
  <md-content class="md-scrollbar">
    <div
      :id="`table${index}`"
      class="table-name"
      :key="key"
      v-for="(key, index) in Object.keys(getMetaData)"
      @click="showColumn(key)"
    >
      <div class="flex">
        <span>{{ key }}</span>
        <md-icon class="icon" v-show="!showColumns[key]">keyboard_arrow_down</md-icon>
        <md-icon class="icon" v-show="showColumns[key]">keyboard_arrow_up</md-icon>
      </div>
      <transition name="slide-down">
        <div class="column" v-show="showColumns[key]">
          <div :key="column.columnName" v-for="column in getMetaData[key]">
            {{ `${column.columnName}(${column.columnType})` }}
          </div>
        </div>
      </transition>
    </div>
  </md-content>
</template>

<script>
  import Vue from 'vue'
  export default {
    name: 'TableBar',
    data: () => ({
      showColumns: {}
    }),
    methods: {
      showColumn(key) {
        Vue.set(this.showColumns, key, !this.showColumns[key])
      }
    },
    computed: {
      getMetaData() {
        return this.$store.getters.getMetaData
      }
    },
  }
</script>

<style lang="scss" scoped>
  .md-content {
    max-height: 500px;
    overflow: auto;
    .table-name {
      cursor: pointer;
      text-align: center;
      border: 1px solid #dcdcdc;
      &:not(#table0) {
        margin-top: 5px;
      }
      &:hover {
        background-color: #dcdcdc;
      }
      .column {
        text-align: left;
      }
      .flex {
        display: flex;
        span {
          flex: auto;
          justify-content: center;
        }
        .icon {
          margin-left: auto;
        }
      }
    }
  }
</style>
