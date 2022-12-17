<template>
  <div class="contain">
    <div class="wr">
      <Player></Player>
      <Title></Title>
      <Input class="input"></Input>
    </div>
  </div>
</template>

<script setup lang="ts">
import Player from "@/components/videoPlayer/player.vue";
import Input from "./input.vue";
import Title from "./title.vue";
</script>

<style scoped>
.contain {
  width: 1200px;
  height: calc(100vh - var(--header-height));
  /* margin-left: 15px; */
  margin-right: 15px;
}
.contain .wr {
  height: calc(100vh - var(--header-height));
  overflow: scroll;
}
.contain .wr::-webkit-scrollbar {
  width: 0 !important;
}
.input {
  margin-top: 50px;
}
</style>
