import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
const timestamp = new Date().getTime();

import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  // base: "./",
  build: {
    manifest: false,
    outDir: "dist",
    assetsDir: "assets",
    cssCodeSplit: false,
    rollupOptions: {
      output: {
        entryFileNames: `assets/[name].${timestamp}.js`,
        chunkFileNames: `assets/[name].${timestamp}.js`,
        assetFileNames: `assets/[name].${timestamp}.[ext]`,
      },
    },
  },
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: '@import "@/assets/style/global.scss";',
      },
    },
  },
  server: {
    // proxy: { //server 开启， 生产模式
    //   '/api': {
    //     changeOrigin: true,
    //     target: 'http://yourdomain:9504',
    //     // rewrite: (path) => path.replace(/^\/tp5\/index\/rsdemo/, '')
    //   }
    // },
    proxy: {
      "/api": {
        // 开发模式  ，后面请求 就需要带上 这一字符串
        changeOrigin: true,
        target: "http://18.163.79.28:8082", // http:// 不可少， 如果只写 localhost:3001 代理会失败
        rewrite: (path) => path.replace(/^\/api/, ""),
        secure: false,
      },
    },
  },
});
