<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="info-wr">
    <div class="l">
      <img :src="userInfo.portrait" alt="" />
    </div>
    <div class="r">
      <div>
        <div class="name">
          <span>{{ userInfo.name }}</span>
          <img :src="imgSrc" alt="" class="subscribeBtn" @click="handleFollow" />
        </div>
      </div>
      <div class="followers">{{ followers }} Subscribers</div>
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
import IMG1 from "@/assets/icons/button_subscribed.svg";
import IMG2 from "@/assets/icons/button_subscribe.svg";
//若无登录，为非follow状态
//若登录，需要根据接口去获取follow状态
// 若点击按钮，需要发送请求给后台更改状态
const loginStore = useLoginStore();
const liveStore = useLiveStore();
const isLogin = loginStore.isLogin;
const { roomInfo,userInfo } = storeToRefs(liveStore);
let followers = ref(0);
const userId = userInfo.value.id;
followers.value = userInfo.value.followedNumber

let followedState = ref(false);
const getState = async () => {
  // console.log(userId);
  const { data: reqData } = await getFollowState(userId);
  const { code, result, message } = reqData;
  const isFollowed = result && result!.isFollowed;
  // console.log(followedState, "followedState");
  return isLogin && isFollowed;
};

onMounted(async () => {
  followedState.value = await getState();
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
  width: 266px;
height: 72px;
  display: flex;
  justify-content: space-between;
align-items: center;
border-radius: 8px;
background: linear-gradient(257deg, rgba(64,227,255,0.30) 0%, rgba(245,72,248,0.30) 100%);
background-color: rgba(0, 0, 0, 0.6);
backdrop-filter: blur(60px);

}
.l {
  width: 42px;
  height: 42px;
  margin-right: 10px;
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
    margin-bottom: 15px;
    opacity: 1;

/* Jukeyz */


	
font-family: HarmonyOS_Sans_SC_Bold;
font-size: 15px;
font-weight: bold;
line-height: 24px;
letter-spacing: 0em;
	
color: #FFFFFF;
	
    img.subscribeBtn {
      width: 120px;
      height: 48px;
      vertical-align: middle;
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
