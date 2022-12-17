<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="playerwr">
    <div id="xg"></div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import { usePlayer } from "./composable/usePlayer";

onMounted(() => {
  const player = usePlayer("#xg");
});
</script>

<style scoped lang="scss">
.playerwr {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>
