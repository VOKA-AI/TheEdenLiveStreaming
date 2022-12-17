<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="title-wr">
    <div class="top">
      <div class="l">此处为视频标题</div>
      <div class="r">
        <span>
          <img src="@/assets/icons/Button_Media_Share.svg" alt="" />
          6286
        </span>
        <span>
          <img src="@/assets/icons/Button_Media_Share.svg" alt="" />
          1250
        </span>
        <span>
          <img src="@/assets/icons/Button_Media_Share.svg" alt="" />
          4558
        </span>
      </div>
    </div>
    <div class="info">
      <span>
        <img src="@/assets/icons/Icon_Media_VV.svg" alt="" />
        :6286
      </span>
      <span>
        <img src="@/assets/icons/Icon_Media_Comments.svg" alt="" />
        :6286
      </span>
      <span>日期</span>
    </div>
    <div class="introduce">
      <p>
        视频简介 That default 0.5 motion blur isn't "made up" at all - It is
        half the frame rate (also known as a 180degree shutter). A bit like the
        specular slider on the principled BSDF - you don't want to change this.
        I think Andrew is coming at this from a stills photography background,
        where adjusting shutter speed to account for light is acceptable in most
        cases - however this is not so for moving image. One of the most common
        mistakes a novice video creator makes is to shoot with a random shutter
        speed. You should always aim to shoot with a shutter speed half that of
        the frame rate you are shooting at if you want natural looking motion -
        Change the ISO, aperture and amount of light in your scene to compensate
        for exposure, not shutter speed when shooting motion.
      </p>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.title-wr {
  width: 1200px;
  padding-left: 12px;
  padding-right: 12px;
  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .l {
      /* 此处为视频标题 */
      height: 15px;
      font-family: HarmonyOS_Sans_SC_Bold;
      font-size: 15px;
      font-weight: bold;
      line-height: 15px;
      letter-spacing: 0em;
      color: #ffffff;
    }
    .r {
      span {
        margin-left: 30px;
        font-size: 12px;
        color: #ffffff;
      }
    }
  }
  .info {
    margin-top: 9px;
    margin-bottom: 12px;
    img {
      height: 12px;
      margin-right: 5px;
    }
    span {
      margin-right: 15px;
      font-size: 12px;
      color: #808080;
    }
  }
  .introduce {
    p {
      font-size: 12px;
      color: #808080;
    }
  }
}
</style>
