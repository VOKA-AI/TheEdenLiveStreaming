<!-- eslint-disable vue/require-v-for-key -->
<template>
  <main class="carousel">
    <controls
      @refresh="refresh"
      @to-left="turn(false)"
      @to-right="turn(true)"
      class="controls"
    ></controls>
    <div class="carousel-chart no-rem">
      <div
        v-for="(item, index) in msg"
        :key="index"
        class="card"
        :class="{ [item.class]: true }"
      >
        <img
          :src="item.info.img"
          class="img"
          @click="toLiveRoom(item.info.name as string)"
        />
      </div>
    </div>
  </main>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import type { Ref } from "vue";
// import { CaretLeft, CaretRight } from "@element-plus/icons-vue";
import { reqGetCarousel } from "@/request/home";
import { useRouter } from "vue-router";
import Controls from "../controls/controls.vue";
import JPG1 from "@/assets/img/1.jpg";
import JPG2 from "@/assets/img/2.jpg";
import JPG4 from "@/assets/img/4.jpg";
import JPG5 from "@/assets/img/5.jpg";
const router = useRouter();

type Msg = {
  info: {
    [key: string]: Array<object> | string | number;
    img: string;
    avatar: string;
  };
  class: string;
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
    class: "c3",
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
    class: "c4",
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
    class: "c5",
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
    class: "c1",
  },
  {
    info: {
      avatar: JPG5,
      img: JPG4,
      name: "5",
      spectatorNum: "200",
      introduction:
        "Hi! 我是Uzra\r\n爐石戰記測試第二季亞洲第一，狼人戰牌組的創始者\r\n遊戲資歷超過10年，參加過大大小小比賽\r\n\r\n[點擊這裡] 來和我一起互動聊天吧",
    },
    class: "c2",
  },
]);

const turn = (flag: boolean = true) => {
  // true --> turn right
  msg.value.forEach((val) => {
    const num = Number(val.class.slice(1));
    const className =
      flag === true
        ? num === 5
          ? "c1"
          : "c" + (num + 1)
        : num === 1
        ? "c5"
        : "c" + (num - 1);
    val.class = className;
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
  //console.log("carousel reflesh");
  const { data } = await reqGetCarousel();
  if (data.code === 0) {
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
    for (let i = 1; i <= 5; i++) {
      const idx = i <= 3 ? i + 2 : i - 3;
      msg.value[i - 1].class = "c" + idx;
    }
  }
};

onMounted(async () => {
  refresh();
});
</script>

<style scoped lang="scss">
.carousel {
  box-sizing: border-box;

  height: 405px;
  width: 1720px;
  padding: 0 var(--home-main-padding);
  margin: 0 auto;

  .carousel-chart {
    width: 720px;
    height: 405px;
    margin: 30px auto 50px;
    position: relative;
    transform-style: preserve-3d;
    perspective: 2000px;
    .card {
      width: 720px;

      position: absolute;
      top: 0;
      left: 0;
      transition: transform 0.5s;
      display: flex;
      .img {
        width: 720px;
        height: 300px;
        border-radius: 15px;
        // transition: width 0.5s;
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
}
.c1 {
  transform: translate3d(-370px, 0, 0);
}
.c2 {
  transform: translate3d(-165px, 0, 200px);
}
.c3 {
  transform: translate3d(-0, 0, 400px);
}
.c4 {
  transform: translate3d(165px, 0, 200px);
}
.c5 {
  transform: translate3d(370px, 0, 0px);
}
</style>
