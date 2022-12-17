<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="category">
    <all-video-and-live
      type="0"
      header-start="right"
      scale="middle"
      :typeOrUserId="typeId"
    ></all-video-and-live>
  </div>
</template>

<script setup lang="ts">
import AllVideoAndLive from "@/components/liveCard/allVideoAndLive.vue";
import { useRoute, onBeforeRouteUpdate } from "vue-router";
import { ref } from "vue";
const route = useRoute();
const parmas = route.params;
const typeId = ref(Number(parmas.typeId));
onBeforeRouteUpdate((to) => {
  console.log("update");
  typeId.value = Number(to.params.typeId);
});
</script>

<style lang="scss" scoped>
.category {
  min-height: 100vh;
}
</style>
