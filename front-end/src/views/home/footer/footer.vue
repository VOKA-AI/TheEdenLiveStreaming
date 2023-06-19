<template>
  <footer v-if="!loginstore.isLogin" class="footer">
    <div class="wrap">
      <img src="@/assets/icons/dog.svg" alt="" class="icon" />
      <span class="context"
        >Join Community of The Eden! Browse the most fantastic streaming
        content.</span
      >
    </div>
    <div class="sign-up" @click="signUp">SIGN UP</div>
  </footer>
</template>

<script setup lang="ts">
import { useLoginStore } from "@/stores/login";
const loginstore = useLoginStore();
const signUp = () => {
  loginstore.changeShowLoginStatus(true);
  loginstore.changeSignUpOrInStatus(false);
};
</script>

<style lang="scss" scoped>
footer {
  width: 100vw;
  height: 46px;
  opacity: 1;
  line-height: 46px;
  box-sizing: border-box;

  background: linear-gradient(
    91deg,
    rgba(64, 227, 255, 0.3) 0%,
    rgba(245, 72, 248, 0.3) 100%
  );

  backdrop-filter: blur(60px);

  text-align: center;
  color: #fff !important;

  position: relative;

  z-index: 9;
  .wrap {
    line-height: 46px;
    vertical-align: middle;
    position: relative;
    display: inline-block;
    color: #FFFFFF;
  }
  .context {
    font-family: HarmonyOS_Sans_Bold;
font-size: 16px;
font-weight: bold;
line-height: 16px;
letter-spacing: 0em;

color: #FFFFFF;
  }
  .sign-up {
    position: absolute;
    padding: 0 10px;
    background-color: #fff;
    color: #000000;
    border-radius: 10px;
    height: 30px;
    box-sizing: border-box;
    top: 8px;
    right: 150px;
    line-height: 30px;

    // font-family: HarmonyOS_Sans_SC_Black;
    font-size: 14px;
    // font-weight: 900;
    font-weight: bolder;

    cursor: pointer;
  }

  .icon {
    font-size: 17px;
    padding-top: 8px;
    position: absolute;
    left: -38px;
    color: #fff;
  }
}
</style>
