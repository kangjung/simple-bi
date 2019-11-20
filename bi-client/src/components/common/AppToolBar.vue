<template>
  <div class="md-toolbar-row">
    <md-button class="md-icon-button" @click="setVisible">
      <md-icon>menu</md-icon>
    </md-button>
    <span class="md-title">Simple BI</span>
    <md-menu :md-active="active" md-size="medium">
      <md-button class="md-icon-button" @click="setActive">
        <md-icon>language</md-icon>
      </md-button>
      <md-menu-content>
        <md-menu-item>
          <md-button class="language-button" @click="changeLang('kr')">
            <span class="flag-icon flag-icon-kr" />
            {{ $t('Korean') }}
          </md-button>
        </md-menu-item>
        <md-menu-item>
          <md-button class="language-button" @click="changeLang('en')">
            <span class="flag-icon flag-icon-us" />
            {{ $t('English') }}
          </md-button>
        </md-menu-item>
      </md-menu-content>
    </md-menu>
  </div>
</template>

<style lang="scss" scoped>
  .md-title {
    flex-grow: 1;
  }
  .test .md-ripple {
    background-color: black;
  }
  .language-button {
    width: 100%;
  }
</style>

<script>
  export default {
    name: 'AppToolBar',
    data: () => ({
      active: false
    }),
    props: {
      visible: Boolean
    },
    methods: {
      setActive() {
        this.active = !this.active
      },
      setVisible() {
        this.$emit('set-visible', !this.visible)
      },
      changeLang(lang) {
        this.$i18n.locale = lang
        this.setActive()
      }
    }
  }
</script>
