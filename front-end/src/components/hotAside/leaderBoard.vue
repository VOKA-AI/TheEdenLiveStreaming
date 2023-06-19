<template>
  <div class="hot-wr">
    <h3 class="title">Streaming Heet <img src="@/assets/icons/icon_board_heet.svg" alt=""></h3>
    <div class="hot-con" v-for="item in rooms">
      <div class="avator">
        <img :src="item.userPortraitUrl" alt="" />
      </div>
      <div class="name">
        <p class="hostname">{{ item.userName }}</p>
        <p class="game">{{ item.title }}</p>
      </div>
      <div class="num">
        <img src="@/assets/icons/icon_board_views.svg" alt="" />
        <span class="watchnum">{{ item.onlineNumber }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getRoomByType } from "@/request/liveRoom";
import { useLiveStore } from "@/stores/live";
import type { roomInfo } from "@/stores/live";
import { onMounted, ref, watch } from "vue";

let rooms = ref<roomInfo[]>([]);
const liveStore = useLiveStore();
const typeId = liveStore.liveTypeId;
onMounted(async () => {
  const { data: roomsData } = await getRoomByType(typeId, 0, 10);
  const { code, result } = roomsData;
  if (code === 0) {
    rooms.value = result.records;
  }
});
watch(
  () => typeId,
  async () => {
    const { data: roomsData } = await getRoomByType(typeId, 0, 10);
    const { code, result } = roomsData;
    if (code === 0) {
      rooms.value = result.records;
    }
  }
);
</script>

<style scoped lang="scss">
h3 {
  /* # 当前分区直播中热度 */
  width: var(--home-left-side-width);
  /* # 当前分区直播中热度 */
  margin-bottom: 15px;
  opacity: 1;

  font-size: 14px;
  font-weight: bold;
  line-height: 15px;
  text-transform: uppercase;
  letter-spacing: 0em;
  color: #ffffff;
  overflow: hidden;
img{
  vertical-align: middle;

}
}
.hot-wr {
  width: 180px;
  padding-left: var(--left-padding-l);
  padding-right: var(--left-padding-r);
  box-sizing: border-box;
}
.hot-con {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
  .avator {
    width: 30px;
    height: 30px;
    border-radius: 50%;
    img {
      width: 30px;
      height: 30px;
      border-radius: 50%;
    }
    background-color: #ffffff;
    margin-right: 5px;
  }
  p {
    width: 70px;
    font-family: HarmonyOS_Sans_Light;
    font-size: 9px;
    font-weight: 300;
    line-height: 15px;
    letter-spacing: 0em;

    color: #ffffff;
  }
  .num {
    img {
      margin-right: 5px;
    }
    color: #ffffff;
    .watchnum {
      /* HarmonyOS_Sans_Light-9pt */
      font-family: HarmonyOS_Sans_Light;
      font-size: 9px;
      font-weight: 300;
      line-height: 9px;
      letter-spacing: 0em;

      color: #00fff7;
    }
  }
}
</style>
