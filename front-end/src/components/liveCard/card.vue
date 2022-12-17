<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div
    class="card"
    :class="{
      large: props.scale === 'large',
      middle: props.scale === 'middle',
    }"
  >
    <img
      :src="loadingImg(props.imgUrl)"
      alt=""
      class="img"
      @click="enterLiveRoom"
    />
    <div class="title">
      {{ props.title }}
    </div>
    <ul class="data">
      <li>
        <img src="@/assets/icons/Icon_Media_VV.svg" alt="" />
        <span>{{ props.playbackVolume }}</span>
      </li>
      <li>
        <img src="@/assets/icons/Icon_Media_Comments.svg" alt="" />
        <span>{{ props.commentNum }}</span>
      </li>
      <li>
        <span>{{ dealDate(props.date) }}</span>
      </li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { loadingImg } from "@/utils/loadingImg";
import IMG1 from "@/assets/img/1.jpg";
import { dealDate } from "@/utils/dealDate";

type Props = {
  width?: string | number;
  height?: string | number;
  imgUrl?: string;
  title: string;
  playbackVolume: number;
  commentNum: number;
  date: string | Date | number;
  scale?: "large" | "middle" | "small";
};

const emit = defineEmits(["enterLiveRoom"]);

const props = withDefaults(defineProps<Props>(), {
  scale: "large",
  imgUrl: IMG1,
});

const enterLiveRoom = () => {
  emit("enterLiveRoom");
};
</script>

<style scoped lang="scss">
.card {
  .img {
    margin-bottom: 10px;
    width: 214px;
    height: 120px;
  }
  .title {
    width: 214px;
    overflow: hidden;
    text-overflow: ellipsis;
    font-size: 15px;
    font-size: 15px;
    font-weight: bold;
    line-height: 15px;
    letter-spacing: 0em;

    color: #ffffff;
  }
  .data {
    display: block;
    width: 214px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: flex;
    list-style: none;
    margin-top: 5px;
    font-size: 12px;

    & > li {
      margin-right: 15px;
    }
  }
}

.card.middle {
  .img {
    width: 294px;
    height: 165px;
  }
  .title,
  .data {
    width: 294px;
  }
}

.card.large {
  .img {
    width: 360px;
    height: 216px;
  }
  .title,
  .data {
    width: 360px;
  }
}
</style>
