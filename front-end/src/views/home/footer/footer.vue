<template>
  <footer v-if="!loginstore.isLogin">
    <div>
      <img src="@/assets/icons/Dev_AMA.svg" alt="" />
      <span class="context"
        >Join Community of The Eden! Browse the most fantastic streaming content.</span
      >
    </div>
    <div class="sign-up" @click="signUp">Sign Up</div>
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
  width: calc(100vw - var(--hot-aside-width));
  height: 46px;
  opacity: 1;
  line-height: 46px;
  box-sizing: border-box;

  background: linear-gradient(91deg, #40e3ff 0%, #f548f8 100%);
  text-align: center;

  position: relative;
  .context {
    opacity: 1;
    font-family: HarmonyOS_Sans_Black;
    font-size: 17px;
    font-weight: 900;
    line-height: 17px;
    letter-spacing: 0em;
    color: #000000;
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

    font-family: HarmonyOS_Sans_SC_Black;
    font-size: 14px;
    font-weight: 900;
    text-transform: uppercase;
    letter-spacing: 0em;

    cursor: pointer;
  }
}
</style>
