const MonacoEditorPlugin = require('monaco-editor-webpack-plugin');

module.exports = {
  // TODO(kuckjwi): After server build.
  // assetsDir: '../bi-server',
  // outputDir: '../bi-server'
  configureWebpack: {
    plugins: [
      new MonacoEditorPlugin({
        languages: ['sql']
      })
    ],
  },
  devServer: {
    proxy: {
      '^/api': {
        target: 'http://localhost:8000',
        changeOrigin: true,
      }
    }
  }
}
