<template>
  <div class="intro-wr">
    <div class="l-wr">
      <p class="roomname">{{ title }}</p>
      <p>
        <span class="tags" v-for="item in tags" key="item.id">{{
          item.tag
        }}</span>
        <!-- <span class="total">
          <img src="@/assets/icons/Icon_Media_VV.svg" alt="" />
          :{{ onlineNumber }}
        </span> -->
      </p>
      <p class="advertisement">
      <img src="@/assets/img/advertisement.jpg" alt="" >
      </p>
    </div>
    <div class="r-wr">
      <!-- <div class="l">
                <img src="@/assets/icons/Avatar_Homepage_User.svg" alt="">
        
        </div> -->
      <!-- <div class="r">
            <div class="top">
                <span class="name">视频发布者</span>
                <img src="@/assets/icons/Button_Media_Follow.svg" alt="">
            </div>
            <div class="bottom">
            99.2万位订阅者
            </div>
        </div> -->
      <div class="icons">
        <span
          ><img src="@/assets/icons/media_vv.svg" alt="" />{{ onlineNumber }} views
          now</span
        >
        <span
          ><img src="@/assets/icons/Button_Media_Subscribe.svg" alt=""
        /></span>
        <span><img src="@/assets/icons/Button_Media_Share.svg" alt="" /></span>
        <span><img src="@/assets/icons/button_video_more.svg" alt="" /></span>
      </div>
      <Info></Info>
      <div class="intro">
        <p class="intro-top">Broadcaster's Noticeboard</p>
        <p class="intro-body">
          learning controller day 3... #1 rated solo players in my room - ads
          are COOL, they support your streamer
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Info from "@/components/info/info.vue";
import { useLiveStore } from "@/stores/live";
const liveStore = useLiveStore();
const title = liveStore.roomInfo.title;
const tags = liveStore.roomInfo.tags;
const onlineNumber = liveStore.roomInfo.onlineNumber;
</script>

<style scoped lang="scss">
.intro-wr {
  width: 100%;
  display: flex;
  justify-content: space-between;
}
.l-wr {
  width: 923px;
  .roomname {
    /* 直播标题 */
    height: 15px;
    opacity: 1;
    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 15px;
    font-weight: bold;
    line-height: 15px;
    letter-spacing: 0em;
    color: #ffffff;
    margin-bottom: 14px;
  }
  .tags {
    display: inline-block;
    margin-right: 8px;

    font-family: HarmonyOS_Sans_Light;
    font-size: 12px;
    font-weight: 400;
    line-height: 12px;
    letter-spacing: 0em;

    color: #00b9ff;
    border-radius: 8px;
    opacity: 0.4;

    padding: 5px 10px;
    background: linear-gradient(
      240deg,
      rgba(64, 227, 255, 0.3) 2%,
      rgba(245, 72, 248, 0.3) 100%
    );

    backdrop-filter: blur(60px);
  }
  .total {
    /* ：当前播放量 */
    height: 9px;
    opacity: 1;
    font-family: HarmonyOS_Sans_Light;
    font-size: 9px;
    font-weight: 300;
    line-height: 9px;
    letter-spacing: 0em;
    color: #808080;
  }
  .advertisement{
    width: 100%;
height: 82px;
img{
  width: 100%;
height: 82px;
}
  }
}
.r-wr {
  // display: flex;
  // justify-content: space-between;
  width: 266px;
  .icons {
    height: 15px;
    margin-bottom: 15px;
    display: flex;
    justify-content: space-between;

    border-radius: 8px ;

    font-family: HarmonyOS_Sans;
    font-size: 12px;
    font-weight: normal;
    line-height: 12px;
    letter-spacing: 0em;
    color: #ffffff;
    img{
      vertical-align: middle;
      margin-right: 2px;
    }
  }
  .icons:first-child {
    color: #00b9ff;
  }
  .intro {
    width: 266px;
    border-radius: 8px;
    margin-top: 10px;
    background: linear-gradient(
      257deg,
      rgba(64, 227, 255, 0.3) 0%,
      rgba(245, 72, 248, 0.3) 100%
    );
    background-color: rgba(0, 0, 0, 0.6);
    backdrop-filter: blur(60px);
    color: #808080;
    p{
      padding-left: 10px;
      padding-right: 10px;
      box-sizing: border-box;
      font-family: HarmonyOS_Sans_SC;
font-size: 12px;
font-weight: normal;
letter-spacing: 0em;
      
    }
    .intro-top{
      height: 22px;
      border-radius: 8px 0px;
font-family: HarmonyOS_Sans_SC;
font-size: 14px;
font-weight: normal;
letter-spacing: 0em;




background-color: rgba(255, 255, 255, 0.1);

backdrop-filter: blur(60px);
    }
  }
}
</style>
