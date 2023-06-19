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
  // test: {
  // },
  server: {
    proxy: {
      "/api": {
        // 开发模式  ，后面请求 就需要带上 这一字符串
        changeOrigin: true,
        target: "http://a383f24e2aa7548eda410f9c34041802-1913302708.ap-northeast-1.elb.amazonaws.com:8082",
        //target: "http://localhost:8082",
        rewrite: (path) => path.replace(/^\/api/, ""),
        secure: false,
      },
    },
  },
});
