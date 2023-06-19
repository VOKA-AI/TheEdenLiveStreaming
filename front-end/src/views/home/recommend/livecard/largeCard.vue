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
            <div class="live">Live</div>
            <div class="spectator">{{ item.onlineNumber }} Watching</div>
            <div class="nickname">{{ item.userName }}</div>
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

const { toLeft, refresh, toRight, pageConfig, liveCard, isShow } =
  usePagenation(props.cardType);

const activeLiveCard = computed(() => {
  return liveCard.value.slice(
    (pageConfig.currentPage - 1) * pageConfig.pagesize,
    pageConfig.currentPage * pageConfig.pagesize
  );
});

const toUserDetail = (userId: number) => {
  const UserSelfId = loginStore.isLogin ? loginStore.userData.name : "u";

  //console.log(UserSelfId, userId);
  //console.log("userdata", loginStore.userData);

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
    .wrap {
      display: flex;
      flex-wrap: nowrap;
      transition: transform 0.3s;
      .a-live-card {
        position: relative;
        margin: 0;
        padding: 0;
        margin-right: 5px;
        width: 290px;
        border-radius: 5px;
        overflow: hidden;

        .cover-img {
          width: 290px;
          height: 165px;
          padding-bottom: 10px;
        }

        .live {
          position: absolute;
          left: 20px;
          top: 20px;
          background-color: red;
          border-radius: 5px;
          font-size: 20px;
          color: #fff;
          padding: 2px 5px;
        }
        .nickname {
          left: 20px;
          bottom: 30px;
          position: absolute;
          font-size: 20px;
          border-radius: 5px;
          color: #fff;
          background-color: rgba(0, 0, 0, 0.357);
          padding: 2px 5px;
        }
        .spectator {
          right: 20px;
          bottom: 30px;
          position: absolute;
          font-size: 20px;
          border-radius: 5px;
          color: #fff;
          background-color: rgba(0, 0, 0, 0.357);
          padding: 2px 5px;
        }

        .dest {
          display: flex;
          min-height: 80px;
          .avatar {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            margin-right: 10px;
          }

          .live-title {
            font-size: 20px;
          }
          .user-name,
          .info {
            text-overflow: ellipsis;
            white-space: nowrap;
            width: 220px;
            overflow: hidden;
          }
          .user-name,
          .info {
            font-size: 9px;
            color: #999;
          }
          .tag-box {
            margin-left: -6px;
            padding-top: 0px;
            max-height: 90px;
            overflow: hidden;
          }

          span.tag {
            font-size: 12px;
            display: inline-block;
            background-color: #aaa;
            padding: 0 10px;
            margin: 2px 5px;
            border-radius: 10px;
            white-space: nowrap;
          }
        }
      }
    }
  }
}
</style>
