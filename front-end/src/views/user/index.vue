<!-- eslint-disable vue/multi-word-component-names -->
<!-- eslint-disable vue/valid-v-for -->
<!-- eslint-disable vue/no-unused-vars -->

<template>
  <div class="user">
    <div class="hot">
      <p class="title">主播最热视频</p>
      <div class="content-box">
        <card
          title="ss"
          :date="new Date()"
          :playback-volume="100"
          :comment-num="100"
          v-for="item in 3"
        >
        </card>
      </div>
    </div>
    <all-video-and-live
      scale="small"
      isByType="1"
      headerStart="left"
      :typeOrUserId="Number(route.params.userId)"
    ></all-video-and-live>
  </div>
</template>

<script setup lang="ts">
import Card from "@/components/liveCard/card.vue";
import allVideoAndLive from "@/components/liveCard/allVideoAndLive.vue";
import { useRoute } from "vue-router";
import { loadingImg } from "@/utils/loadingImg";
loadingImg;

const route = useRoute();
</script>

<style scoped lang="scss">
.user {
  padding: 45px 15px;
  color: #fff;
  overflow-y: auto;
  height: calc(100vh - var(--header-height));
  -ms-overflow-style: none;
  overflow: -moz-scrollbars-none;
  &::-webkit-scrollbar {
    width: 0 !important;
  }

  .hot {
    display: flex;
    justify-content: center;
    flex-direction: column;
    align-items: center;
    .title {
      font-size: 20px;
      font-weight: bold;
      line-height: 20px;
      letter-spacing: 0em;

      color: #ffffff;
      margin-bottom: 15px;
    }
    .content-box {
      display: flex;
      width: 1180px;
      justify-content: space-around;
      flex-wrap: nowrap;
    }
  }
}
</style>
