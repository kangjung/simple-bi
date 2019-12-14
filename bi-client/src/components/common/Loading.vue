<template>
  <Loading
    :active.sync="loading"
    color="#448aff"
    :is-full-page="true"
  />
</template>

<script>
  import Loading from 'vue-loading-overlay';
  export default {
    name: 'LoadingBar',
    components: {
      Loading,
    },
    props: {
      loading: {
        type: Boolean,
        required: true,
      }
    }
  }
</script>
