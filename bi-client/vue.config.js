module.exports = {
  // TODO(kuckjwi): After server build.
  // assetsDir: '../bi-server',
  // outputDir: '../bi-server'
  devServer: {
    proxy: {
      '^/api': {
        target: 'http://localhost:8000/api'
      }
    }
  }
}
