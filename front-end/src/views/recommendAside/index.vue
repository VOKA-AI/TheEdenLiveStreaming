<!-- eslint-disable vue/multi-word-component-names -->
<!-- eslint-disable vue/no-unused-vars -->
<!-- eslint-disable vue/valid-v-for -->
<template>
  <div class="aside-wr">
    <div class="contain">
      <VideoCard v-for="n in 12"></VideoCard>
    </div>
  </div>
</template>

<script setup lang="ts">
import VideoCard from "@/components/videoCard/videoCard.vue";
</script>

<style scoped lang="scss">
.aside-wr {
  width: 400px;
  height: calc(100vh - var(--header-height));
  overflow: hidden;
}
.contain {
  height: calc(100vh - var(--header-height));
  padding-right: 94px;
  background-color: #000;
  overflow: scroll;
}
.contain::-webkit-scrollbar {
  width: 0 !important;
}
</style>
