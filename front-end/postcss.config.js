module.exports = {
  plugins: {
    autoprefixer: {
      overrideBrowserslist: [
        "Android 4.1",
        "iOS 7.1",
        "Chrome > 31",
        "ff > 31",
        "ie >= 8",
        "last 10 versions", // 所有主流浏览器最近10版本用
      ],
      grid: true,
    },
    "postcss-pxtorem": {
      rootValue: 192,
      propList: ["*"],
      unitPrecision: 6,
      selectorBlackList: [".no-rem",".ad"], // 不转
      mediaQuery: false, // 允许在媒体查询中转换px。
      minPixelValue: 2, // 设置要替换的最小像素值。
      exclude: [/node_modules/i], // 排除 node_modules 文件(node_modules 内文件禁止转换)
    },
  },
};
