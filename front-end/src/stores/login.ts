import { defineStore } from "pinia";
import { ref, reactive } from "vue";

export type UserData = {
  name: string;
  id: number | string;
  portrait: string;
};

export const useLoginStore = defineStore("login", () => {
  // 是否弹出登录页   登录还是注册
  const showLogin = ref(false);
  const signUpOrIn = ref(true);
  const isLogin = ref(false);
  const userData: UserData = reactive({
    name: "",
    id: "",
    portrait: "",
  });

  // 改变 弹出登录页 状态
  const changeShowLoginStatus = (val: boolean) => {
    showLogin.value = val;
  };
  // 改变是登录还是注册
  const changeSignUpOrInStatus = (val: boolean) => {
    signUpOrIn.value = val;
  };
  return {
    changeShowLoginStatus,
    changeSignUpOrInStatus,
    signUpOrIn,
    showLogin,
    isLogin,
    userData,
  };
});
