<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="info-wr">
    <div class="l">
      <img :src="roomInfo.userPortraitUrl" alt="" />
    </div>
    <div class="r">
      <div>
        <div class="name">
          <span>{{ roomInfo.userName }}</span>
          <img :src="imgSrc" alt="" @click="handleFollow" />
        </div>
      </div>
      <div class="followers">{{ followers }} 订阅者</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getFollowers } from "@/request/liveRoom";
import {
  getFollowState,
  setFollowState,
  setUnfollowState,
} from "@/request/user";
import { useLiveStore } from "@/stores/live";
import { useLoginStore } from "@/stores/login";
import { loadingImg } from "@/utils/loadingImg";
import { ElMessage } from "element-plus";
import { storeToRefs } from "pinia";
import { computed, onMounted, ref } from "vue";
import IMG1 from "@/assets/icons/Button_Media_Followed.svg";
import IMG2 from "@/assets/icons/Button_Media_Follow.svg";
//若无登录，为非follow状态
//若登录，需要根据接口去获取follow状态
// 若点击按钮，需要发送请求给后台更改状态
const loginStore = useLoginStore();
const liveStore = useLiveStore();
const isLogin = loginStore.isLogin;
const { roomInfo } = storeToRefs(liveStore);
const userId = roomInfo.value.userId;
let followedState = ref(false);
const getState = async () => {
  // console.log(userId);
  const { data: reqData } = await getFollowState(userId);
  const { code, result, message } = reqData;
  const isFollowed = result && result!.isFollowed;
  // console.log(followedState, "followedState");
  return isLogin && isFollowed;
};
let followers = ref(0);
const getFollowersNum = async () => {
  const { data: reqdata } = await getFollowers(userId);
  return reqdata.result;
};
onMounted(async () => {
  followedState.value = await getState();
  followers.value = await getFollowersNum();
  console.log(followers.value, "follow");
});
const handleFollow = async () => {
  //follow --> unfollow
  if (followedState.value) {
    await setUnfollowState(userId);
    followedState.value = false;
    followers.value -= 1;
  } else {
    const { data: reqData } = await setFollowState(userId);
    const { code, result, message } = reqData;
    if (code === 500) {
      ElMessage({
        type: "info",
        message: message,
      });
      return;
    }
    followedState.value = true;
    followers.value += 1;
  }
};
const imgSrc = computed(() => {
  let src = followedState.value ? IMG1 : IMG2;
  // 此处需要特殊处理
  return loadingImg(src);
});
</script>

<style scoped lang="scss">
.info-wr {
  width: 280px;
  display: flex;
  justify-content: space-between;
}
.l {
  width: 42px;
  height: 42px;
  border-radius: 50%;
  img {
    width: 42px;
    height: 42px;
    border-radius: 50%;
  }
}
.r {
  width: 228px;
  .name {
    /* 视频发布者名 */
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 24px;
    opacity: 1;

    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 15px;
    font-weight: bold;
    line-height: 24px;
    letter-spacing: 0em;

    color: #ffffff;
    img {
      width: 72px;
      height: 24px;
      vertical-align: center;
    }
  }
  .followers {
    height: 12px;
    margin-top: 8px;
    opacity: 1;
    font-family: HarmonyOS_Sans_Light;
    font-size: 12px;
    line-height: 12px;
    letter-spacing: 0em;
    color: #ffffff;
  }
}
</style>
