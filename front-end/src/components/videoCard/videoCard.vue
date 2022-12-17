<template>
  <div class="card-wr">
    <div class="l">
      <img src="" alt="" />
    </div>
    <div class="r">
      <div class="top">
        <p>推荐视频标题推荐视频标题推荐 视频标题</p>
      </div>
      <div class="bottom">
        <p>主播名</p>
        <p>
          <span>播放量：</span>
          <span>日期</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.card-wr {
  width: 307px;
  height: 80px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  .l {
    width: 142px;
    height: 80px;
    background: linear-gradient(119deg, #525252 0%, #404040 100%);
    img {
      width: 142px;
      height: 80px;
    }
  }
  .r {
    width: 155px;
    height: 80px;
    .top {
      width: 155px;
      height: 30px;
      margin-bottom: 12px;
      overflow: hidden;
      p {
        line-height: 14px;
      }
    }
    .bottom {
      width: 155px;
      height: 40px;
      p {
        margin-bottom: 5px;
      }
    }
    p {
      /* 推荐视频标题推荐视频标题推荐 视频标题 */
      width: 155px;
      font-family: HarmonyOS_Sans_Light;
      font-size: 12px;
      line-height: 12px;
      letter-spacing: 0em;
      color: #808080;
    }
  }
}
</style>
