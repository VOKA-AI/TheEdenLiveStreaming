<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="playerwr">
    <video id="video" controls autoplay style="width:100%;height:100%;"></video>
    <div id="status"></div>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import { loadHLS } from "./composable/usePlayerIPFS";

onMounted(() => {
  loadHLS();
})

</script>

<style scoped lang="scss">
.playerwr {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>
