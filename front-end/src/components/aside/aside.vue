<template>
  <div class="release-wr">
    <Info></Info>
    <!-- <Comment></Comment> -->
    <AsideComp>
      <slot class="slot"></slot>
    </AsideComp>
  </div>
</template>

<script setup lang="ts">
import Info from "../info/info.vue";
import AsideComp from "./asideComp.vue";
// import Comment from './comment.vue'
</script>

<style scoped lang="scss">
.release-wr {
  width: 300px;
  height: calc(100vh - var(--header-height));
  background-color: #000;
  box-sizing: border-box;
  padding-left: 10px;
}
.slot {
  height: calc(100vh - 100px);
  overflow-y: scroll;
  background: #1f1f23;
}
</style>
