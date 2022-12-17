<!-- eslint-disable vue/multi-word-component-names -->
<!-- eslint-disable vue/require-v-for-key -->
<template>
  <div class="header">
    <div class="left">
      <span class="name" @click="toHome"> The Eden </span>
      <img src="@/assets/icons/Button_User_Notice.svg" alt="" />
    </div>
    <div class="middle">
      <span class="sorts" v-for="(item, index) in allType" :class="{
        active: liveStore.liveTypeId === index + 1 && route.name === 'category',
      }" @click="typeClick(index, item.typeId)">
        <img :src="loadingImg(item?.iconUrl)" /></span>
    </div>
    <div class="right">
      <img src="@/assets/icons/Button_Search_.svg" alt="" class="search" />
      <div class="before-login" v-if="!loginstore.isLogin">
        <span class="sign-in" @click="login(true)"> SIGN IN</span>
        <div class="sign-up" @click="login(false)">SIGN UP</div>
      </div>
      <div class="logined" v-else>
        <img src="@/assets/icons/Button_User_Streaming.svg" alt="" class="live" @click="handleLiveClick" />
        <img src="@/assets/icons/Button_User_Upload.svg" alt="" class="upload" />
        <img src="@/assets/icons/Button_User_Notice.svg" alt="" class="notice" />
        <img :src="loginstore.userData.portrait" alt="" class="avatar" ref="avatar" />
        <ul class="dropdown-user-menu" v-show="dropdownUserMenu">
          <li @click="logout">登出</li>
        </ul>
      </div>
    </div>
  </div>
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
const avatar = ref();

const dropdownUserMenu = ref(false);

// 点击登录、注册
const login = (flag: boolean) => {
  loginstore.changeShowLoginStatus(true);
  loginstore.changeSignUpOrInStatus(flag);
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

document.addEventListener("click", function (e) {
  if (e.target === avatar.value) {
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
  // console.log(1);

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
  width: 1920px;
  line-height: var(--header-height);
  position: fixed;
  margin-bottom: var(--header-height);
  z-index: 99;
  background-color: #fff;

  // margin-bottom: 50px;
  /* 矩形 3 */
  opacity: 1;
  background: #18181b;
  box-shadow: 0px 1px 0px 0px rgba(0, 0, 0, 0.2);

  .left {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 180px;
    height: var(--header-height);
    line-height: var(--header-height);
    padding: 0 var(--body-padding-l);

    .name {
      font-family: Skygraze;
      font-size: 20px;
      color: #ffffff;

      cursor: pointer;
    }
  }

  .right {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 400px;
    height: var(--header-height);
    line-height: var(--header-height);

    img {
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
        color: #000;
        border-radius: 9px;
        opacity: 1;
        background: linear-gradient(108deg, #40e3ff 0%, #f548f8 99%);
        padding: 0 8px;
        height: 24px;
        line-height: 24px;
        cursor: pointer;
        text-align: center;
      }
    }

    .logined {
      .dropdown-user-menu {
        list-style: none;
        position: absolute;
        width: 60px;
        background-color: #ffffff;
        border-radius: 10px;
        top: 40px;
        right: -0px;

        &>li {
          text-align: center;
          cursor: pointer;
        }
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
    width: 1303px;
    height: var(--header-height);
    line-height: var(--header-height);
    margin-left: var(--middle-margin-l);
    margin-right: var(--middle-margin-r);
    padding-left: 108px;

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

.configure {
  position: fixed;
  top: 0;
}
</style>
