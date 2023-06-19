<script setup lang="ts">
import HeaderVue from "@/components/header/header.vue";
import { useRouter, useRoute } from "vue-router";
import { isLogin } from "./utils/isLogin";
const router = useRouter();
const route = useRoute();
isLogin(router, route);
</script>

<template>
  <header-vue></header-vue>
  <div class="main-wr">
    <router-view class="left" name="left"></router-view>
    <router-view class="main"></router-view>
    <router-view class="right" name="right"></router-view>
  </div>
</template>

<style scoped lang="scss">
.main-wr {
  display: flex;
  overflow: hidden;
  padding-top: var(--header-height);
  background: url("@/assets/img/bg.jpg") no-repeat center;
  background-size: cover;
  height: 100vh;
  .left,
  .right {
    height: calc(100vh - var(--header-height));
  }
  .left {
    background: rgba(31, 31, 35, 0.01);

backdrop-filter: blur(60px);
box-shadow: 0px 3px 6px 0px rgba(0, 0, 0, 0.16);
  }

  .main {
    flex: 1;
    overflow-y: auto;
    height: calc(100vh - var(--header-height));
    background: rgba(0, 0, 0, 0);
    -ms-overflow-style: none;
    overflow: -moz-scrollbars-none;
    &::-webkit-scrollbar {
      width: 0 !important;
    }
  }
}
</style>
