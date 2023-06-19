<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="wr">
    <div class="main middle">
      <!-- <player :path="userData.livePath"></player> -->
      <!-- <introduce :title="userData.title" :intoduction="userData.intoduction" :userName="userData.userName"></introduce> -->
      <player></player>
      <introduce class="intro"></introduce>
      <history></history>
    </div>
  </div>
</template>

<script setup lang="ts">
import Player from "@/components/videoPlayer/player.vue";
import introduce from "./introduce/index.vue";
import history from "@/views/liveRoom/history/index.vue";
import { onBeforeRouteUpdate, useRoute } from "vue-router";
import { getLiveRoom } from "@/request/config";
import { onMounted, ref } from "vue";
import type { Ref } from "vue";
import { useLiveStore, type roomInfo } from "@/stores/live";
const route = useRoute();

const userData: Ref<roomInfo | {}> = ref({});
const liveStore = useLiveStore();
onMounted(async () => {
  const req = await getLiveRoom(route.params.id as string);
  const val = req!.data!.result;
  userData.value = req!.data!.result;
  //console.log(val)
  liveStore.setRoomInfo(val);
});
onBeforeRouteUpdate(async (to, from) => {
  //console.log(to, from, 666);
  if (to.params.id !== from.params.id) {
    userData.value = await getLiveRoom(to.params.id as string);
  }
  return true;
});
</script>

<style scoped lang="scss">
.wr {
  // margin-top: var(--header-height);
  // padding-top: var(--header-height);
  width: 100%;
  height: calc(100vh - var(--header-height));
}
.intro {
  margin-top: 20px;
}
.main {
  overflow-y: scroll;
  box-sizing: border-box;
}
.main::-webkit-scrollbar {
  width: 0 !important;
}
.middle {
  padding-left: var(--middle-margin-l);
  padding-right: var(--middle-margin-r);
}
</style>
