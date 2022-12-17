<!-- eslint-disable vue/require-v-for-key -->
<template>
  <controls
    @refresh="refresh"
    @to-left="turn(false)"
    @to-right="turn(true)"
    class="controls"
  ></controls>
  <div class="carousel-chart">
    <div
      v-for="(item, index) in msg"
      :key="index"
      class="card"
      :style="{
        transform: `translate3d(${item.style.x}px, 0px,${item.style.z}px)`,
      }"
    >
      <img
        :src="item.info.img"
        class="img"
        :class="{ 'active-img': item.style.active }"
        @click="toLiveRoom(item.info.name as string)"
      />
      <div class="msg" :class="{ 'active-msg': item.style.active }">
        <div class="top">
          <img :src="loadingImg(item.info.avatar)" alt="" class="avatar" />
          <div class="user-info">
            <p class="name">{{ item.info.name }}</p>
            <p class="num">{{ item.info.spectatorNum }}名观众</p>
          </div>
        </div>
        <div class="bottom ad">
          {{ item.info.introduction }}
        </div>
      </div>
    </div>
    <!-- 
    <el-icon @click="turn(true)" class="right">
      <CaretRight />
    </el-icon>
    <el-icon @click="turn(false)" class="left">
      <CaretLeft />
    </el-icon> -->
  </div>
</template>

<script setup lang="ts">
import { loadingImg } from "@/utils/loadingImg";
import { ref, onMounted } from "vue";
import type { Ref } from "vue";
// import { CaretLeft, CaretRight } from "@element-plus/icons-vue";
import { reqGetCarousel } from "@/request/home";
import { useRouter } from "vue-router";
import Controls from "../controls/controls.vue";
import JPG1 from "@/assets/img/1.jpg";
import JPG2 from "@/assets/img/2.jpg";
import JPG3 from "@/assets/img/3.jpg";
import JPG4 from "@/assets/img/4.jpg";
import JPG5 from "@/assets/img/5.jpg";
const router = useRouter();

type Msg = {
  info: {
    [key: string]: Array<object> | string | number;
    img: string;
    avatar: string;
  };
  style: {
    x: number;
    z: number;
    active: boolean;
  };
};

const msg: Ref<Msg[]> = ref([
  {
    info: {
      avatar: JPG5,
      img: JPG1,
      name: "1",
      spectatorNum: "100",
      introduction:
        "Hi! 我是Uzra\r\n爐石戰記測試第二季亞洲第一，狼人戰牌組的創始者\r\n遊戲資歷超過10年，參加過大大小小比賽\r\n\r\n[點擊這裡] 來和我一起互動聊天吧",
    },
    style: {
      z: 300,
      x: 0,
      active: true,
    },
  },
  {
    info: {
      avatar: JPG5,
      img: JPG2,
      name: "2",
      spectatorNum: "200",
      introduction:
        "Hi! 我是Uzra\r\n爐石戰記測試第二季亞洲第一，狼人戰牌組的創始者\r\n遊戲資歷超過10年，參加過大大小小比賽\r\n\r\n[點擊這裡] 來和我一起互動聊天吧",
    },
    style: {
      z: 0,
      x: 300,
      active: false,
    },
  },
  {
    info: {
      avatar: JPG5,
      img: JPG4,
      name: "3",
      spectatorNum: "200",
      introduction:
        "Hi! 我是Uzra\r\n爐石戰記測試第二季亞洲第一，狼人戰牌組的創始者\r\n遊戲資歷超過10年，參加過大大小小比賽\r\n\r\n[點擊這裡] 來和我一起互動聊天吧",
    },
    style: {
      z: -300,
      x: 0,
      active: false,
    },
  },
  {
    info: {
      avatar: JPG5,
      img: JPG4,
      name: "4",
      spectatorNum: "200",
      introduction:
        "Hi! 我是Uzra\r\n爐石戰記測試第二季亞洲第一，狼人戰牌組的創始者\r\n遊戲資歷超過10年，參加過大大小小比賽\r\n\r\n[點擊這裡] 來和我一起互動聊天吧",
    },
    style: {
      z: 0,
      x: -300,
      active: false,
    },
  },
]);

const style = [
  {
    z: 300,
    x: 0,
    active: true,
  },
  {
    z: 0,
    x: 300,
    active: false,
  },
  {
    z: -300,
    x: 0,
    active: false,
  },
  {
    z: 0,
    x: -300,
    active: false,
  },
];

const turn = (flag: boolean = true) => {
  const tem = flag ? 300 : -300;
  msg.value.forEach((v) => {
    if (v.style.x === 0 && v.style.z === 300) {
      v.style.x = tem;
      v.style.z = 0;
      v.style.active = false;
    } else if (v.style.x === 300 && v.style.z === 0) {
      v.style.x = 0;
      v.style.z = -tem;
      if (!flag) v.style.active = true;
    } else if (v.style.x === 0 && v.style.z === -300) {
      v.style.x = -tem;
      v.style.z = 0;
    } else if (v.style.x === -300 && v.style.z === 0) {
      v.style.x = 0;
      v.style.z = tem;
      if (flag) v.style.active = true;
    }
  });
};

const toLiveRoom = (userName: string) => {
  router.push({
    name: "liveRoom",
    params: {
      id: userName,
    },
  });
};

const refresh = async () => {
  console.log("carousel reflesh");
  const { data } = await reqGetCarousel();
  if (data.code === 0) {
    console.log("carousel reflesh susscss");

    const result = data.result;

    result.forEach((val: any, index: number) => {
      const {
        coverUrl: img,
        userPortraitUrl: avatar,
        userName: name,
        roomId,
        livePath,
        onlineNumber: spectatorNum,
        infoId,
        title,
        introduction,
        userId,
      } = val;
      msg.value[index].info = {
        img,
        avatar,
        name,
        roomId,
        livePath,
        spectatorNum,
        infoId,
        title,
        introduction,
        userId,
      };
    });
    for (let i = 0; i < 4; i++) {
      const { active, x, z } = style[i];
      // console.log(i, active, x, z);

      msg.value[i].style = {
        active: active,
        x: x,
        z: z,
      };
    }
  }
};

onMounted(async () => {
  refresh();
});
</script>

<style scoped lang="scss">
.controls {
  padding-right: 152px;
}

.carousel-chart {
  width: 800px;
  height: 302px;
  // margin-top: 30px;
  // padding-top: 130px;
  // margin-left: 500px;
  margin: 0 auto 50px;
  position: relative;
  transform-style: preserve-3d;
  perspective: 4000px;
  top: 40px;

  .card {
    width: 800px;
    height: 302px;
    // padding: 20px 0;
    border: 1px solid #666;

    position: absolute;
    top: 0;
    left: 0;
    // float: left;
    transition: transform 0.5s;

    display: flex;

    .img {
      width: 798px;
      height: 300px;
      transition: width 0.5s;
    }

    .active-img {
      width: 600px;
    }

    .msg {
      width: 0;
      height: 300px;
      background-color: #666;
      overflow: hidden;
      transition: width 0.5s;
    }

    .active-msg {
      width: 198px;
    }

    .top {
      display: flex;
      width: 198px !important;
      .avatar {
        width: 50px;
        height: 50px;
        margin: 10px;
      }

      .name {
        font-size: 22px;
        margin-top: 20px;
      }
    }

    .bottom {
      width: 198px !important;
      box-sizing: border-box;
      padding: 10px;
      overflow: hidden;
      // white-space: break-all;
      white-space: pre-line;
      word-break: break-word;
      text-align: left;
    }
  }

  .left,
  .right {
    color: #fff;
    position: absolute;
    top: 130px;
    font-size: 50px;
    z-index: 9999;
    transform: translateZ(1000px);
  }

  .left {
    left: -180px;
  }

  .right {
    right: -180px;
  }
}

.ad {
  font-size: 12px;
}
</style>
