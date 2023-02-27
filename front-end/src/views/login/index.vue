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
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;

  background: rgba(128, 128, 128, 0.01);

  backdrop-filter: blur(60px);

  z-index: 150;

  .card {
    width: 420px;
    margin: 30px auto;

    width: 580px;
    border-radius: 4px;
    opacity: 1;

    background: #1f1f23;
    border: 0;

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
      padding: 0 5px;
      font-weight: bold;

      cursor: pointer;

      border-bottom: 3px solid transparent;
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
