<!-- eslint-disable vue/valid-v-for -->
<template>
  <div class="all">
    <div class="header" v-if="props.scale === 'small'">
      <div
        class="live header-btn"
        :class="{ active: isActive }"
        @click="isActive = true"
      >
        往期直播
      </div>
      <div
        class="vidos header-btn"
        :class="{ active: !isActive }"
        @click="isActive = false"
      >
        视频
      </div>
    </div>
    <main class="content" :class="{ middle: props.scale === 'middle' }">
      <card
        :scale="props.scale"
        :title="item.title"
        :img-url="item.coverUrl"
        :playback-volume="item.onlineNumber || 0"
        :comment-num="0"
        :date="item.startTime || new Date()"
        v-for="item in cardData"
        class="card"
        @enter-live-room="enterLiveRoom(item.userName)"
      ></card>
    </main>
    <footer>
      <el-pagination
        class="pagination"
        background
        layout="prev, pager, next"
        :hide-on-single-page="true"
        v-model:current-page="pageConfig.currentPage"
        v-model:page-size="pageConfig.pageSize"
        :total="pageConfig.total"
        @update:current-page="update"
        @update:page-size="sizeUpdate"
      />
    </footer>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import Card from "./card.vue";
import { useRequest } from "./composable/useRequest";
const router = useRouter();
type Props = {
  isByType?: "0" | "1"; // 0 为分类页，1为个人页
  headerStart: "left" | "right";
  scale: "middle" | "small";
  typeId?: number;
  typeOrUserId: number;
};

const props = withDefaults(defineProps<Props>(), {
  isByType: "0",
  headerStart: "left",
  scale: "small",
});

const update = () => {};
const sizeUpdate = () => {};

const enterLiveRoom = (name: string) => {
  if (props.isByType === "0") {
    router.push({
      name: "liveRoom",
      params: {
        id: name,
      },
    });
  }
};

const { cardData, pageConfig, isActive } = useRequest(props.isByType, props);
</script>

<style scoped lang="scss">
.all {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  .header {
    color: #fff;
    display: flex;
    width: 100%;
    justify-content: v-bind("headerStart");
    margin-bottom: 10px;
    padding: 0 100px 0 70px;
    box-sizing: border-box;
    margin-top: 32px;
    .header-btn {
      width: 90px;
      height: 24px;
      border-radius: 9px;
      opacity: 1;

      font-size: 15px;
      font-weight: bold;

      text-align: center;
      line-height: 24px;
      margin-right: 10px;
      cursor: pointer;
    }

    .header-btn.active {
      background: linear-gradient(105deg, #40e3ff 0%, #f548f8 100%);
    }
  }

  .content {
    width: 1180px;
    opacity: 1;
    display: flex;
    flex-wrap: wrap;
    // justify-content: space-around;
    justify-content: flex-start;
    align-content: flex-start;
    margin-bottom: 30px;
    padding: 0 15px;
    box-sizing: border-box;
    .card {
      margin-right: 15px;
      color: #fff;
    }
  }
  .content.middle {
    padding: 0;
    margin-top: 150px;
    width: 1500px;
    justify-content: flex-start;
    .card {
      margin-right: 5px;
    }
  }
  footer {
    .pagination {
      text-align: center;
      margin: 0 auto;
    }

    :deep(.el-pager .number) {
      background-color: #000;
      box-sizing: border-box;
      border: 1px solid #fff;
      border-radius: 8px;
    }

    :deep(.el-pager .number.is-active) {
      border: none;
    }
    :deep(.btn-next),
    :deep(.btn-prev) {
      border-radius: 50%;
      background-color: #000 !important;
      border: 1px solid #00b9ff;
      color: #fff;
      width: 28px !important;
      min-width: 28px !important;
      height: 28px !important;
      box-sizing: border-box;
    }
  }
}
</style>
