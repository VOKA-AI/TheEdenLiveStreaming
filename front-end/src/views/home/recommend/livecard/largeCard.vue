<!-- eslint-disable vue/require-v-for-key -->
<template>
  <div class="card" v-if="isShow">
    <controls
      @to-left="toLeft"
      @refresh="refresh"
      @to-right="toRight"
      class="controls"
    ></controls>
    <div class="live-card">
      <div class="wrap" ref="wrap">
        <div class="a-live-card" v-for="item in activeLiveCard">
          <div style="position: relative">
            <div class="live">直播</div>
            <div class="spectator">{{ item.onlineNumber }}名观众</div>
            <img
              :src="item.coverUrl"
              alt=""
              class="cover-img"
              @click="toDetailRoom(item.userName)"
            />
          </div>
          <div class="dest">
            <img
              :src="item.userPortraitUrl || ''"
              alt=""
              class="avatar"
              @click="toUserDetail(item.userId)"
            />
            <div>
              <div class="live-title">{{ item.title }}</div>
              <div class="user-name">{{ item.userName }}</div>
              <div class="info">{{ item.introduction }}</div>
              <div class="tag-box">
                <span class="tag" v-for="tag in item.tags">{{ tag.tag }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import Controls from "../../controls/controls.vue";

import { useRouter } from "vue-router";
import { usePagenation } from "./composable/usePagenation";
import { useLoginStore } from "@/stores/login";
const loginStore = useLoginStore();

const router = useRouter();

const props = withDefaults(defineProps<{ cardType: 0 | 1 }>(), {
  cardType: 0,
});

const wrap = ref();

const toDetailRoom = (id: string) => {
  router.push({
    name: "liveRoom",
    params: {
      id: id,
    },
  });
};

const { toLeft, refresh, toRight, pageConfig, liveCard, isShow } = usePagenation(
  props.cardType
);

const activeLiveCard = computed(() => {
  return liveCard.value.slice(
    (pageConfig.currentPage - 1) * pageConfig.pagesize,
    pageConfig.currentPage * pageConfig.pagesize
  );
});

const toUserDetail = (userId: number) => {
  const UserSelfId = loginStore.isLogin ? loginStore.userData.name : "u";

  console.log(UserSelfId, userId);
  console.log("userdata", loginStore.userData);

  router.push({
    name: "user",
    params: {
      id: UserSelfId.toString(),
      userId: userId.toString(),
    },
  });
};

onMounted(() => {
  refresh();
});
</script>

<style lang="scss" scoped>
.card {
  height: 300px;
  .live-card {
    width: 1502px;
    margin: 10px 0;
    padding: 0px;
    color: #fff;
    overflow-y: hidden;

    overflow-x: auto;
    -ms-overflow-style: none;
    overflow: -moz-scrollbars-none;
    &::-webkit-scrollbar {
      width: 0 !important;
    }
    // justify-content: space-around;
    .wrap {
      // width: 100%;
      display: flex;
      flex-wrap: nowrap;
      transition: transform 0.3s;
      transform: translate3d(0, 0, 0);
      .a-live-card {
        position: relative;
        margin-right: 8px;

        .cover-img {
          width: 294px;
          height: 165px;
          padding-bottom: 10px;
        }

        .live {
          position: absolute;
          left: 20px;
          top: 20px;
          background-color: red;
          color: #fff;
          padding: 2px 5px;
        }

        .spectator {
          left: 20px;
          bottom: 30px;
          position: absolute;
          color: #fff;
          background-color: rgba(0, 0, 0, 0.357);
          padding: 2px 5px;
        }

        .dest {
          display: flex;
          .avatar {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            margin: 0 10px 0 10px;
          }

          .live-title {
            text-overflow: ellipsis;
            white-space: nowrap;
            width: 220px;
            overflow: hidden;

            font-size: 11px;
            font-weight: 500;
            line-height: 14px;
          }

          span.tag {
            background-color: #aaa;
            padding: 0 10px;
            margin: 0 5px;
            border-radius: 10px;
          }
        }
      }
    }
  }

  .controls {
    box-sizing: border-box;
    padding-right: 85px;
  }
}
</style>
