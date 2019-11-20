<template>
  <div>
    <md-toolbar class="md-transparent" md-elevation="1">
      {{ $t('Menu') }}
    </md-toolbar>
    <md-list>
      <md-list-item to="/" exact>
        <md-icon>dashboard</md-icon>
        <span class="md-list-item-text">{{ $t('DashBoard') }}</span>
      </md-list-item>
      <md-list-item to="/datasource">
        <md-icon>data_usage</md-icon>
        <span class="md-list-item-text">{{ $t('Data Source') }}</span>
      </md-list-item>
      <md-list-item to="/dataconnection">
        <md-icon>power</md-icon>
        <span class="md-list-item-text">{{ $t('Data Connection') }}</span>
      </md-list-item>
    </md-list>
  </div>
</template>

<script>
export default {
  name: 'AppNavBar'
}
</script>
