<template>
  <div>
    <Aside class="aside">
      <div class="content">
        <p class="title">简介</p>
        <p class="info">该用户很懒，没有设置</p>
      </div>
    </Aside>
  </div>
</template>

<script setup lang="ts">
import Aside from "@/components/aside/aside.vue";
</script>

<style scoped lang="scss">
.content {
  box-sizing: border-box;
  color: #fff;
  padding: 10px;
  .title {
    font-size: 15px;
    font-family: HarmonyOS Sans SC-Bold, HarmonyOS Sans SC;
    font-weight: bold;
    color: #ffffff;
    line-height: 0px;
    -webkit-text-fill-color: transparent;
  }

  .info {
    background: #1f1f23;
    border-radius: 0px 0px 0px 0px;
    opacity: 1;
  }
}
</style>
