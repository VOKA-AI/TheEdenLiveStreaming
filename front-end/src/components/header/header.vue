<!-- eslint-disable vue/multi-word-component-names -->
<!-- eslint-disable vue/require-v-for-key -->
<template>
  <div class="header">
    <div class="left">
      <span class="name" @click="toHome"> The Eden </span>
      <img src="@/assets/icons/Button_User_Notice.svg" ref="tools" alt="" />
      <ul class="dropdown-user-menu" v-show="dropdownUserMenu">
        <p>GENERAL</p>
        <li>
          <a href="#">About</a>
        </li>
        <li>
          <a href="#">Advertisers</a>
        </li>
        <li>
          <a href="#">Blog </a>
        </li>
        <li>
          <a href="">Developers</a>
        </li>
        <li>
          <a href="#">Download Apps</a>
        </li>
        <li>
          <a href="#"> Gift Card</a>
        </li>
        <li>
          <a href="#">Jobs</a>
        </li>
        <li>
          <a href="#"> Store</a>
        </li>
        <li>
          <a href="#">Music</a>
        </li>
        <p>HELP & LEGAL</p>
        <li>
          <a href="#"> Accessiblility Statement</a>
        </li>
        <li>
          <a href="#"> Ad Choices</a>
        </li>
        <li>
          <a href="#"> Community Guidelines</a>
        </li>
        <li>
          <a href="#">Cookie Poilcy</a>
        </li>
        <li>
          <a href="#"> Help</a>
        </li>
        <li>
          <a href="#">Privacy Policy</a>
        </li>
        <li>
          <a href="#">Safety Center</a>
        </li>
        <li>
          <a href="#"> Security</a>
        </li>
        <li>
          <a href="#"> terms</a>
        </li>
      </ul>
    </div>
    <div class="middle">
      <span
        class="sorts"
        v-for="(item, index) in allType"
        :class="{
          active:
            liveStore.liveTypeId === index + 1 && route.name === 'category',
        }"
        @click="typeClick(index, item.typeId)"
      >
        {{item?.type}}
        <!-- <img :src="loadingImg(item?.iconUrl)"/> -->
      </span>
    </div>
    <div class="right">
      <img src="@/assets/icons/Button_Search_.svg" alt="" class="search" />
      <div class="before-login" v-if="!loginstore.isLogin">
        <img src="@/assets/icons/metamask-fox.svg" @click="MetaLogin" />
        <span class="sign-in" @click="login(true)"> SIGN IN</span>
        <div class="sign-up" @click="login(false)">SIGN UP</div>
      </div>
      <div class="logined" v-else>
        <img
          src="@/assets/icons/Button_User_Streaming.svg"
          alt=""
          class="live"
          @click="handleLiveClick"
        />
        <img
          src="@/assets/icons/Button_User_Upload.svg"
          alt=""
          class="upload"
        />
        <img
          src="@/assets/icons/Button_User_Notice.svg"
          alt=""
          class="notice"
        />
        <img
          :src="loginstore.userData.portrait"
          alt=""
          class="avatar"
          ref="avatar"
          @click="logout"
        />
      </div>
    </div>
    \
  </div>
  <div class="style"></div>

  <teleport to="body">
    <configure v-if="liveStore.isShowConfig" class="configure"></configure>
    <Login class="login"></Login>
  </teleport>
</template>
<script setup lang="ts">
import { ref } from "vue";
import type { Ref } from "vue";

import { useLiveStore } from "@/stores/live";
import { useLoginStore } from "@/stores/login";
import { removeStorage, removeTokenAUTH } from "@/utils/localStorage";
import { useRouter, useRoute } from "vue-router";
import { reqGetAllType } from "@/request/header";
import { loadingImg } from "@/utils/loadingImg";
import Configure from "@/views/configure/index.vue";
import Login from "@/views/login/index.vue";
import MetaMaskOnboarding from "@metamask/onboarding";
import { ElMessage, type FormInstance } from "element-plus";

const router = useRouter();
const route = useRoute();

type Type = {
  type: string;
  typeId: number;
  iconUrl?: string;
};

const allType: Ref<Type[]> = ref([]);

const loginstore = useLoginStore();
const liveStore = useLiveStore();

// 头像refs
const tools = ref();

const dropdownUserMenu = ref(false);

const onboarding = new MetaMaskOnboarding();
const { ethereum } = window as any;

// 点击登录、注册
const login = (flag: boolean) => {
  loginstore.changeShowLoginStatus(true);
  loginstore.changeSignUpOrInStatus(flag);
};

//Created check function to see if the MetaMask extension is installed
const isMetaMaskInstalled = () => {
  //Have to check the ethereum binding on the window object to see if it's installed
  return Boolean(ethereum && ethereum.isMetaMask);
};

const MetaLogin = async () => {
  //Now we check to see if MetaMask is installed
  if (!isMetaMaskInstalled()) {
    //If it isn't installed we ask the user to click to install it
    ElMessage.info("Install MetaMask!");
    onboarding.startOnboarding();
  } else {
    try {
      // Will open the MetaMask UI
      await ethereum.request({ method: "eth_requestAccounts" });
      //we use eth_accounts because it returns a list of addresses owned by us.
      const accounts: string[] = await ethereum.request({
        method: "eth_accounts",
      });
      ElMessage.success("Get Wallet Address Successfully!");
      loginstore.changeShowLoginStatus(false);
      loginstore.isLogin = true;
      // router.push({
      //   name: "home",
      //   params: {
      //     id: data.name.toString(),
      //   },
      // });
      if (accounts.length > 0) {
        //console.log(accounts);
      }
    } catch (error) {
      console.error(error);
    }
  }
};

const logout = () => {
  removeStorage("userData");
  removeTokenAUTH();
  loginstore.isLogin = false;
  loginstore.userData = {
    name: "",
    id: "",
    portrait: "",
  };
  router.push({
    name: "home",
    params: {
      id: "u",
    },
  });
};

const toUser = () => {
  router.push({
    name: "user",
    params: {
      id: loginstore.userData.name,
      userId: loginstore.userData.id,
    },
  });
};

document.addEventListener("click", function (e) {
  if (e.target === tools.value) {
    dropdownUserMenu.value = !dropdownUserMenu.value;
  } else {
    dropdownUserMenu.value = false;
  }
});

//请求
const getAllType = async () => {
  const { data } = await reqGetAllType();
  // console.log(data);
  if (data.code === 0) {
    const result = data.result as Type[];

    allType.value.length = 0;
    // console.log(result);
    allType.value.push(...result);
  } else if (data.code === 401) {
    allType.value = Array(5);
  }
};

getAllType();

const handleLiveClick = () => {
  //console.log("handleLiveClick");
  liveStore.setShowConfig();
};

const toHome = () => {
  const id = loginstore.isLogin ? loginstore.userData.name : "u";
  router.push({
    name: "home",
    params: {
      id: id,
    },
  });
};

const id = loginstore.isLogin ? loginstore.userData.id : "u";
const typeClick = (index: number, typeId: number) => {
  liveStore.setTypeId(typeId);
  //console.log(1);

  router.push({
    name: "category",
    params: {
      id: id,
      typeId: typeId.toString(),
    },
  });
};
</script>
<style scoped lang="scss">
.header {
  display: flex;
  box-sizing: border-box;
  height: var(--header-height);
  width: 100vw;
  box-sizing: border-box;
  padding-right: var(--home-main-padding);
  line-height: var(--header-height);
  position: fixed;
  justify-content: space-between;
  margin-bottom: var(--header-height);
  z-index: 99;
  opacity: 1;

  background: linear-gradient(
    91deg,
    rgba(14, 14, 16, 0.01) 0%,
    rgba(85, 79, 64, 0.01) 80%,
    rgba(127, 127, 127, 0.01) 100%
  );

  backdrop-filter: blur(60px);

  background: linear-gradient(
    91deg,
    rgba(14, 14, 16, 0.01) 0%,
    rgba(85, 79, 64, 0.01) 80%,
    rgba(127, 127, 127, 0.01) 100%
  );

  backdrop-filter: blur(60px);
  box-shadow: 0px 1px 0px 0px rgba(0, 0, 0, 0.2);
  .left {
    display: flex;
    position: relative;
    justify-content: space-between;
    align-items: center;
    width: var(--home-left-side-width);
    height: var(--header-height);
    line-height: var(--header-height);
    padding: 0 var(--body-padding-l);

    .name {
      font-family: Skygraze;
      font-size: 20px;
      color: #ffffff;
      cursor: pointer;
    }
    .dropdown-user-menu {
      width: 178px;
      padding: 0;
      height: 553px;
      opacity: 1;

      background: #1f1f23;

      backdrop-filter: blur(60px);
      border: 1px solid #552576;
      color: #fff;
      list-style: none;
      position: absolute;
      top: 40px;
      right: -100px;
      & > p {
        text-indent: 15px;
        height: 20px;
        font-size: 14px;
        margin-bottom: 20px;
      }

      & > li {
        cursor: pointer;
        height: 26px;
        width: 100%;
        line-height: 26px;

        a {
          display: block;
          width: 178px;
          height: 26px;
          line-height: 26px;
          opacity: 1;
          font-size: 11px;
          color: #ccc;
          transform: scale(0.8);
          text-overflow: ellipsis;
          white-space: nowrap;
          text-decoration: none;
        }
      }
      & > li:hover {
        background-color: #552576;
      }
    }
  }

  .right {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    width: 400px;
    height: var(--header-height);
    line-height: var(--header-height);
    img:not(.avatar) {
      margin-right: 20px;
      height: 15px;
    }

    img .search {
      width: 16px;
      height: 16px;
    }

    img.avatar {
      width: 24px;
      height: 24px;
      border-radius: 50%;
      margin-right: 10px;
    }

    .before-login {
      font-size: 14px;
      font-weight: bold;

      .sign-in {
        color: #fff;
        margin-right: 20px;
        cursor: pointer;
      }

      .sign-up {
        color: #fff;
        border-radius: 9px;
        border-radius: 12px;
        opacity: 1;

        background: linear-gradient(
          108deg,
          rgba(64, 227, 255, 0.3) 0%,
          rgba(245, 72, 248, 0.3) 100%
        );

        backdrop-filter: blur(60px);
        padding: 2px 12px;
        height: 24px;
        line-height: 24px;
        cursor: pointer;
        text-align: center;
        margin-right: 10px;
      }
    }

    .before-login,
    .logined {
      display: flex;
      align-items: center;
      position: relative;
    }
  }

  .middle {
    // width: 1303px;\
    flex: 1;
    height: var(--header-height);
    // line-height: var(--header-height);
    margin-left: var(--middle-margin-l);
    margin-right: var(--middle-margin-r);
    padding-left: 108px;
    overflow: hidden;
    .sorts {
      /* Game */
      width: 79px;
      height: 15px;
      margin-right: 28px;
      opacity: 1;
      font-family: HarmonyOS_Sans_SC_Bold;
      font-size: 15px;
      font-weight: bold;
      line-height: 15px;
      text-transform: uppercase;
      letter-spacing: 0em;
      color: #ffffff;

      position: relative;

      cursor: pointer;

      img {
        height: 18px;
      }
    }

    .active::before {
      content: "";
      left: 50%;
      transform: translateX(-50%);
      bottom: -8px;
      display: block;
      position: absolute;
      width: 70px;
      height: 2px;
      background: linear-gradient(90deg, #40e3ff 0%, #f548f8 100%);
    }
  }
}

.style {
  z-index: 100;
  position: absolute;
  height: var(--header-height);
  width: 100vw;
  background: linear-gradient(
    180deg,
    rgba(14, 14, 16, 0.02) 0%,
    rgba(127, 127, 127, 0.2) 100%
  );
  pointer-events: none;
}
.configure {
  position: fixed;
  top: 0;
}
</style>
