<script setup lang="ts">
import HeaderVue from "@/components/header/header.vue";
import { isLogin } from "./utils/isLogin";
isLogin();
</script>

<template>
  <header-vue></header-vue>
  <!-- <login-vue></login-vue> -->

  <div class="main-wr">
    <router-view class="left" name="left"></router-view>
    <router-view class="main"></router-view>
    <router-view class="right" name="right"></router-view>
    <!-- <div class="right"></div> -->
  </div>
</template>

<style scoped lang="scss">
.main-wr {
  display: flex;
  overflow: hidden;
  padding-top: var(--header-height);
  background-color: #000;
  height: 100vh;
  .left,
  .right {
    height: calc(100vh - var(--header-height));
  }

  .main {
    flex: 1;
    overflow-y: auto;
    height: calc(100vh - var(--header-height));
    -ms-overflow-style: none;
    overflow: -moz-scrollbars-none;
    &::-webkit-scrollbar {
      width: 0 !important;
    }
  }
}
</style>
