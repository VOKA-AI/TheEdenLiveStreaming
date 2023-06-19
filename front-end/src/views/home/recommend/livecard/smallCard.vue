<!-- eslint-disable vue/require-v-for-key -->
<template>
  <controls-vue
    @to-left="toLeft"
    @refresh="refresh"
    @to-right="toRight"
    class="controls"
  ></controls-vue>
  <div class="recommend-card">
    <div
      class="a-card"
      v-for="item in recommendCard"
      @click="toDetailTag(item)"
    >
      <img :src="loadingImg(item.img)" alt="" class="img" />
      <div class="bottom">
        <p class="name">
          <span>{{ item.name }}</span>
          <el-icon class="more-filled">
            <MoreFilled />
          </el-icon>
        </p>
        <p class="spectator">{{ item.spectatorNum }}Watching</p>
        <span class="tag-name">{{ item.tagName }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { loadingImg } from "@/utils/loadingImg";
import { MoreFilled } from "@element-plus/icons-vue";
import controlsVue from "../../controls/controls.vue";

const recommendCard = ref([
  {
    id: 1,
    name: "fda",
    img: "/src/assets/img/1.jpg",
    tagName: "aaa",
    spectatorNum: 33,
  },
  {
    id: 1,
    name: "fda",
    img: "/src/assets/img/1.jpg",
    tagName: "aaa",
    spectatorNum: 33,
  },
  {
    id: 1,
    name: "fda",
    img: "/src/assets/img/1.jpg",
    tagName: "aaa",
    spectatorNum: 33,
  },
  {
    id: 1,
    name: "fda",
    img: "/src/assets/img/1.jpg",
    tagName: "aaa",
    spectatorNum: 33,
  },
]);

const toDetailTag = (item: object) => {
  //console.log(123);
};
const refresh = () => {
  //console.log("refresh");
};

const toLeft = () => {
  //console.log("refresh");
};

const toRight = () => {
  //console.log("refresh");
};
</script>

<style lang="scss" scoped>
.recommend-card {
  // padding: 0 50px;
  box-sizing: border-box;
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  margin: 0 90px;
  padding: 0;
  overflow: hidden;

  color: #fff;

  .a-card {
    width: 150px;
    font-size: 16px;
    border-radius: 5px;

    margin: 20px 42px;

    .img {
      width: 150px;
      height: 200px;
      margin: 0px;
    }

    .bottom {
      padding: 10px 0px;

      .name {
        // font-weight: bold;
        font-size: 11px;
        cursor: pointer;

        .more-filled {
          float: right;
        }
      }

      .name::before {
        content: "";
        display: block;
        clear: both;
      }

      .tag-name {
        cursor: pointer;
        padding: 0px 10px;
        background-color: #808080;
        border-radius: 10px;
        line-height: 10px;
      }

      .spectator {
        font-size: 12px;
      }
    }
  }
}

.controls {
  box-sizing: border-box;
  padding-right: 50px;
}
</style>
