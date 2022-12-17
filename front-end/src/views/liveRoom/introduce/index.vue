<template>
    <div class="intro-wr">
    <div class="l-wr">
    <p class="roomname">{{title}}</p>
    <p>
    <span class="tags" v-for="item in tags" key="item.id">{{item.tag}}</span>
        <span class="total"><img src="@/assets/icons/Icon_Media_VV.svg" alt=""> :{{onlineNumber}}</span>
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
        <Info></Info>
    </div>

    </div>
</template>

<script setup lang="ts">
import Info from '@/components/info/info.vue';
import { useLiveStore } from '@/stores/live';
const liveStore = useLiveStore()
const title = liveStore.roomInfo.title
const tags = liveStore.roomInfo.tags
const onlineNumber = liveStore.roomInfo.onlineNumber

</script>

<style scoped lang="scss">
.intro-wr{
    width: 100%;
    display: flex;
    justify-content:space-between;
}
.l-wr{
    .roomname{
        /* 直播标题 */
    height: 15px;
    opacity: 1;
    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 15px;
    font-weight: bold;
    line-height: 15px;
    letter-spacing: 0em;
    color: #FFFFFF;
    margin-bottom: 14px;
    }
    .tags{
        display: inline-block;
        height: 9px;
        margin-right: 8px;
        font-family: HarmonyOS_Sans_Light;
font-size: 9px;
font-weight: 300;
line-height: 9px;
letter-spacing: 0em;
color: #808080;
    }
    .total{
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
}
.r-wr{
    display: flex;
justify-content: space-between;
width: 293px;
.l{
    width: 42px;
    height: 42px;
    border-radius: 50%;
}
.r{
    width: 241px;
    p{
        width: 151px;
    }
    .top{
        display: flex;
        justify-content: space-between;
        height: 24px;
        line-height: 24px;
        margin-bottom: 8px;
padding-right: 13px;

        .name{
            /* 视频发布者名 */
height: 15px;
opacity: 1;
font-family: HarmonyOS_Sans_SC_Bold;
font-size: 15px;
font-weight: bold;
line-height: 15px;
letter-spacing: 0em;
color: #FFFFFF;
        }
        img{
            vertical-align: top;
        }
    }
    .bottom{
/* 99.3万位订阅者 */

height: 9px;
font-family: HarmonyOS_Sans_Light;
font-size: 9px;
font-weight: 300;
line-height: 9px;
letter-spacing: 0em;
color: #FFFFFF;
    }
}
}
</style>