<template>
  <div class="com-wr">
    <div class="com-contain">
      <!-- <div class="wrap">
        <h3><span class="num">165</span>条评论</h3>
        <div class="comment" v-for="n in 10" >
          <div class="l">
            <img src="@/assets/icons/Avatar_Homepage_User.svg" alt="" />
          </div>
          <div class="r">
            <p class="comname">评论名字</p>
            <p class="comword">
              I think it would be okay to touch specular on materials, but only
              doing so if you know the IOR. In most cases, dielectrics can be
              kept at .5/.425 (depends on who you ask haha), but metals should
              definitely have their specular changed. This is what helps bring
              out the tint desaturating along the Fresnel reflection.
            </p>
          </div>
        </div>
      </div> -->

      <slot :id="{ id: 123 }"></slot>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.com-wr {
  width: 280px;
  padding-left: 10px;
  padding-right: 9px;
  margin-top: 14px;
  box-sizing: border-box;
  .com-contain {
    width: 261px;
    height: calc(100vh - 100px);
    overflow-y: hidden;
    background: #1f1f23;
  }
}
</style>
