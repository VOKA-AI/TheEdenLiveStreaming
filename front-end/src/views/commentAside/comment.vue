<template>
  <div class="wrap">
    <h3><span class="num">165</span>条评论</h3>
    <div class="comment" v-for="n in 10">
      <div class="l">
        <img src="@/assets/icons/Avatar_Homepage_User.svg" alt="" />
      </div>
      <div class="r">
        <p class="comname">评论名字</p>
        <p class="comword">
          I think it would be okay to touch specular on materials, but only
          doing so if you know the IOR. In most cases, dielectrics can be kept
          at .5/.425 (depends on who you ask haha), but metals should definitely
          have their specular changed. This is what helps bring out the tint
          desaturating along the Fresnel reflection.
        </p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
h3 {
  /* 165条评论 */
  height: 40px;
  opacity: 1;
  font-family: HarmonyOS_Sans_SC_Bold;
  font-size: 15px;
  font-weight: bold;
  line-height: 40px;
  letter-spacing: 0em;

  color: #ffffff;
}
.wrap {
  height: calc(100vh - 100px);
  overflow-y: scroll;
  background: #1f1f23;

  .comment {
    display: flex;
    justify-content: space-between;
    width: 261px;
    margin-bottom: 9px;
    .l {
      width: 30px;
    }
    .r {
      width: 226px;
      .comname {
        /* 评论者名 */
        margin-bottom: 5px;
        font-family: HarmonyOS_Sans_Light;
        font-size: 12px;
        font-weight: 3400;
        line-height: 12px;
        letter-spacing: 0em;
        color: #ffffff;
      }
      .comword {
        width: 226px;
        font-size: 12px;
        font-family: HarmonyOS_Sans_Light;
        color: #ffffff;
      }
    }
  }
}
.wrap::-webkit-scrollbar {
  width: 0 !important;
}
</style>
