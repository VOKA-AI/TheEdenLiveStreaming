import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "./assets/style/main.css";
import "@/assets/style/font.css";
// import "amfe-flexible/index.js";
import "@/utils/resize"
import "element-plus/theme-chalk/el-loading.css";
import "element-plus/theme-chalk/el-message.css";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const app = createApp(App);

const pinia = createPinia()
app.use(pinia);
pinia.use(piniaPluginPersistedstate)
app.use(router);

app.mount("#app");
