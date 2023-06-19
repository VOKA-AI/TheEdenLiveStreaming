<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="login" v-show="loginstore.showLogin">
    <el-card class="card">
      <header class="header">
        <h5 class="title left">The Eden</h5>
        <div class="right">
          <span
            class="change-btn"
            @click="loginstore.changeSignUpOrInStatus(true)"
            :class="{ active: loginstore.signUpOrIn }"
            >SIGN IN</span
          >
          <span
            class="change-btn"
            @click="loginstore.changeSignUpOrInStatus(false)"
            :class="{ active: !loginstore.signUpOrIn }"
            >SIGN UP</span
          >
        </div>
      </header>

      <!-- 登录 -->
      <sign-in-vue />
      <!-- 注册 -->
      <sign-up-vue />

      <el-icon class="close" @click="loginstore.changeShowLoginStatus(false)">
        <Close />
      </el-icon>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { useLoginStore } from "@/stores/login";
import { Close } from "@element-plus/icons-vue";

import SignInVue from "./signIn/SignIn.vue";
import SignUpVue from "./signUp/SignUp.vue";

// login 的 vuex
const loginstore = useLoginStore();
/*
 * vuex完成跨文件的事件触发
 * 优点是比较方便
 * 缺点是难以维护和管理
 */
</script>

<style scoped lang="scss">
.login {
  font-size: 16px;

  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  position: fixed;
  top: var(--header-height);
  left: 0;

  color: #fff;

  backdrop-filter: blur(60px);

  z-index: 150;

  .card {
    width: 420px;
    margin: 10px auto;

    width: 580px;
    opacity: 1;

    // background: transparent;
    border: 0;

    border-radius: 24px;
    background: rgba(31, 31, 35, 0.01);
    .header {
      display: block;
      // margin-bottom: 30px;
      overflow: hidden;
      padding: 30px 38px;
    }
    .header::before {
      content: "";
      display: block;
      clear: both;
    }
    .title {
      font-family: Skygraze;
      font-size: 20px;
      font-weight: normal;
      line-height: 20px;
      letter-spacing: 0em;

      color: #ffffff;
      float: left;
      margin-bottom: 10px;
    }
    .right {
      float: right;
      & > span {
        font-family: HarmonyOS_Sans_SC_Bold;
        font-size: 19px;
        font-weight: bold;
        line-height: 19px;
        letter-spacing: 0em;

        color: #ffffff;
      }
    }

    .change-btn {
      display: inline-block;
      position: relative;
      padding: 0 5px;
      font-family: HarmonyOS_Sans_SC_Bold;
      font-size: 19px;
      font-weight: bold;

      cursor: pointer;

      border-bottom: 3px solid transparent;
    }
    .active::before {
      content: "";
      display: block;
      position: absolute;
      bottom: -20px;
      left: 5px;
      width: 80px;
      height: 2px;
      border-radius: 117px;
      opacity: 1;

      background: linear-gradient(90deg, #40e3ff 0%, #f548f8 100%);
    }
    .change-btn:first-of-type {
      margin-right: 18px;
    }
    .close {
      position: absolute;
      top: -28px;
      right: -28px;
      font-size: 30px;
      transition: 0.3s transform;
    }

    .close:hover {
      transform: rotate(90deg);
    }
  }
}
</style>
