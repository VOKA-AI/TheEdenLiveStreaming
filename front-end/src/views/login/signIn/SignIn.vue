<template>
  <!-- 登录 -->
  <el-form
    :model="userLoginMsg"
    v-show="loginstore.signUpOrIn"
    class="form"
    ref="loginForm"
  >
    <el-form-item prop="username" required>
      <p class="lable">Username</p>
      <el-input v-model="userLoginMsg.username" />
    </el-form-item>
    <el-form-item prop="password" required>
      <p class="lable">password</p>
      <el-input
        type="password"
        :show-password="true"
        v-model="userLoginMsg.password"
      />
    </el-form-item>
    <p class="forget-psw">Forget Password?</p>
    <div class="btn">
      <el-button
        class="cancle-btn"
        @click="loginstore.changeShowLoginStatus(false)"
        >Cancle</el-button
      >
      <el-button
        class="login-btn"
        @click="login(loginForm)"
        :disabled="isDisabled"
        >SIGN IN</el-button
      >
    </div>
  </el-form>
</template>

<script setup lang="ts">
import { useLoginStore } from "@/stores/login";
import { ref, reactive } from "vue";
import type { Ref } from "vue";
import { ElMessage, type FormInstance } from "element-plus";
import { reqLogin } from "@/request/login";
import { setTokenAUTH } from "@/utils/localStorage";
import { useRouter } from "vue-router";
const router = useRouter();

type UserLoginMsg = {
  username: string;
  password: string;
};

const loginstore = useLoginStore();
const loginForm = ref();
const isDisabled: Ref<boolean> = ref(false);
const userLoginMsg: UserLoginMsg = reactive({
  username: "",
  password: "",
});

const login = async (formEl: FormInstance) => {
  if (!formEl) return;
  formEl.validate(async (valid) => {
    if (valid) {
      // console.log(userLoginMsg.username, userLoginMsg.password.toString());
      isDisabled.value = true;
      const { data: res } = await reqLogin(
        userLoginMsg.username,
        userLoginMsg.password.toString()
      );
      console.log(res);
      isDisabled.value = false;
      const data = res.result;
      console.log(data);
      if (res.code == 0) {
        setTokenAUTH(data.token);
        loginstore.changeShowLoginStatus(false);
        loginstore.isLogin = true;
        loginstore.userData = {
          name: data.name,
          id: data.id,
          portrait: data.portrait,
        };
        router.push({
          name: "home",
          params: {
            id: data.name.toString(),
          },
        });
        const { disconnect, initSocket } = await import(
          "@/utils/socket/useSocket"
        );
        disconnect();
        initSocket();
      } else if (res.code === 506) {
        userLoginMsg.password = "";
        ElMessage.warning("密码错误");
      } else {
        ElMessage.error({
          message: data.message,
        });
      }
      console.log("submit!");
    } else {
      console.log("error submit!");
      return false;
    }
  });
};
</script>

<style scoped lang="scss">
.form {
  display: flex;
  flex-direction: column;
  align-self: center;
  padding: 0 40px;
  .el-form-item {
    margin-bottom: 30px;
  }

  :deep(.el-input__wrapper) {
    width: 500px;
    height: 42px;
    background-color: #494949 !important;
    box-shadow: none;
  }
  :deep(.el-input__wrapper:focus-within) {
    border: 1px solid #00fff7;
  }
  :deep(input) {
    color: #fff;
  }
  .btn {
    text-align: center;
    .cancle-btn,
    .login-btn {
      border: none;
      font-family: HarmonyOS_Sans_SC_Bold;
      font-size: 15px;
      font-weight: bold;
      line-height: 15px;
      text-align: center;
      letter-spacing: 0em;

      color: #ffffff;
      width: 100px;
      height: 42px;
      border-radius: 4px;
      opacity: 1;
      // margin-top: 50px;
      margin: 50px 20px 30px;
    }
    .login-btn {
      background: linear-gradient(113deg, #40e3ff 1%, #f548f8 100%);
    }
    .cancle-btn {
      background-color: #1f1f23;
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

  .forget-psw {
    font-family: HarmonyOS_Sans_SC_Light;
    font-size: 12px;
    font-weight: 300;
    line-height: 12px;
    letter-spacing: 0em;
    text-decoration: underline;

    color: #ffffff;
    margin-top: -10px;
  }
}
</style>
