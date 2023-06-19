import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "./assets/style/main.css";
import "@/assets/style/font.css";
// import "amfe-flexible/index.js";
import "@/utils/resize";
import "element-plus/theme-chalk/el-loading.css";
import "element-plus/theme-chalk/el-message.css";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

const app = createApp(App);

const pinia = createPinia();

app.provide("backendHost", "http://a383f24e2aa7548eda410f9c34041802-1913302708.ap-northeast-1.elb.amazonaws.com");
//app.provide("backendHost", "https://localhost");
app.provide("srsHost","afe7f4f6461ed43f1bf675186b1135bf-288952435.ap-northeast-1.elb.amazonaws.com");

app.use(pinia);
pinia.use(piniaPluginPersistedstate);
app.use(router);

app.mount("#app");
