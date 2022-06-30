const path = require("path")

/**
 * @type {import('webpack').Configuration}
 */
module.exports = {
  "mode": "development",
  "entry": "./src/index",
  "module": {
    rules: [
      {
        test: /\.tsx?$/,
        use: "ts-loader",
        exclude: /node_modules/
      }
    ]
  },
  resolve: {
    extensions: [".tsx", ".ts", ".js"]
  },
  "devtool": "source-map",
  "output": {
    filename: "bundle.js",
    path: path.resolve(__dirname, "dist")
  }
}