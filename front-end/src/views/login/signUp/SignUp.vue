<template>
  <el-form
    :model="userSignupMsg"
    v-show="!loginstore.signUpOrIn"
    class="form"
    ref="logUpForm"
    :rules="logUpRules"
  >
    <el-form-item prop="username" required>
      <p class="lable">Username</p>
      <el-input v-model="userSignupMsg.username" />
    </el-form-item>
    <el-form-item prop="password" required>
      <p class="lable">password</p>
      <el-input
        type="password"
        :show-password="true"
        v-model="userSignupMsg.password"
      />
    </el-form-item>
    <el-form-item prop="confirm" required>
      <p class="lable">Confirm</p>
      <el-input
        type="password"
        :show-password="true"
        v-model="userSignupMsg.confirm"
      />
    </el-form-item>
    <el-form-item v-if="!useEmail" prop="phone">
      <p class="lable">Phone</p>
      <el-input type="text" v-model="userSignupMsg.phone" />
    </el-form-item>
    <el-form-item v-if="useEmail" prop="mail">
      <p class="lable">Email</p>
      <el-input type="text" v-model="userSignupMsg.mail" />
    </el-form-item>
    <el-form-item prop="code">
      <p class="lable">Captcha Code</p>
      <div>
        <el-input type="text" v-model="userSignupMsg.code" class="code" />
        <el-button class="code-btn" @click="getCode(logUpForm)">
          GET
        </el-button>
      </div>
    </el-form-item>
    <p class="use-email" @click="useEmail = !useEmail">
      Use {{ useEmail ? "phone number" : "Email" }} instead
    </p>
    <div class="btn">
      <el-button
        class="cancle-btn"
        @click="loginstore.changeShowLoginStatus(false)"
        >Cancle</el-button
      >
      <el-button
        class="logup-btn"
        @click="logUp(logUpForm)"
        :disabled="isDisabled"
        >SIGN UP</el-button
      >
    </div>
  </el-form>
</template>

<script setup lang="ts">
import { ref } from "vue";
import type { Ref } from "vue";
import { useLoginStore } from "@/stores/login";
import { logUpRule } from "./composable/useLogupRules";
import { useLogupMethods } from "./composable/useLogupMethods";

const loginstore = useLoginStore();

const logUpForm = ref();

// 使用电子邮箱
const useEmail: Ref<boolean> = ref(false);

// 禁用按钮
const isDisabled: Ref<boolean> = ref(false);

const { logUpRules, userSignupMsg } = logUpRule(logUpForm);

const { logUp, getCode } = useLogupMethods(
  loginstore,
  userSignupMsg,
  useEmail,
  isDisabled
);
</script>

<style scoped lang="scss">
.form {
  display: flex;
  flex-direction: column;
  align-self: center;
  color: #fff;
  padding: 0 40px;

  .use-email {
    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 15px;
    line-height: 15px;
    letter-spacing: 0em;

    color: #00fff7;
    cursor: pointer;
    margin-top: -10px;
    // margin-bottom: 25px;
    width: 250px;
  }

  :deep(input) {
    color: #fff;
  }

  .code {
    width: 300px;
  }
  .code-btn {
    border: none;
    width: 150px;
    background-color: #1f1f23;
    height: 42px;
    margin-left: 10px;
    border-radius: 24px;
  }

  .el-form-item {
    margin-bottom: 30px;
  }

  :deep(.el-input__wrapper) {
    width: 500px;
    height: 42px;
    border-radius: 24px;
    background: #292929;

    box-shadow: inset 0px 3px 6px 0px rgba(0, 0, 0, 0.16);
  }
  :deep(.el-input__wrapper:focus-within) {
    border: 1px solid #00fff7;
  }

  :deep(.el-input) {
    border: none !important;
  }
  .btn {
    text-align: center;
    .cancle-btn,
    .logup-btn {
      font-family: HarmonyOS_Sans_SC_Bold;
      font-size: 15px;
      // font-weight: bold;
      line-height: 15px;
      text-align: center;
      letter-spacing: 0em;

      color: #ffffff;
      border: none;
      width: 100px;
      height: 42px;
      border-radius: 24px;
      opacity: 1;
      // margin-top: 50px;
      margin: 50px 20px 30px;
    }
    .logup-btn {
      border-radius: 24px;

      background: linear-gradient(
        113deg,
        rgba(64, 227, 255, 0.3) 0%,
        rgba(245, 72, 248, 0.3) 100%
      );

      backdrop-filter: blur(60px);
    }
    .cancle-btn {
      background-color: transparent;
    }
  }

  .lable {
    margin-bottom: 10px;
    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 15px;
    font-weight: bold;
    line-height: 15px;
    letter-spacing: 0em;

    color: #ffffff;
  }
}
</style>
