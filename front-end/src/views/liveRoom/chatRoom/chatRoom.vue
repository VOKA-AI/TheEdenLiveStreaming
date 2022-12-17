<!-- eslint-disable vue/require-v-for-key -->
<!-- eslint-disable vue/valid-v-for -->
<template>
  <div class="chat-room">
    <el-icon class="show-room" v-show="hiddenChatRoom">
      <Upload />
    </el-icon>
    <div class="card" :class="{ active: hiddenChatRoom }">
      <div class="header">
        <!-- <el-icon class="to-right">
          <ArrowRight />
        </el-icon> -->
        <p>直播聊天</p>
        <el-icon>
          <User />
        </el-icon>
      </div>
      <main>
        <a-msg-vue
          v-for="chat in chatHistory"
          :name="chat.name"
          :content="chat.content"
          :name-color="chat.nameColor"
          :medal="chat.medal"
        >
        </a-msg-vue>
      </main>
      <footer>
        <div class="pre">
          <el-icon>
            <Star />
          </el-icon>
        </div>
        <div class="suf">
          <el-card class="show-pic" v-show="showPic">
            <el-icon class="close-pic" @click="showPic = false">
              <Close />
            </el-icon>
            <main
              v-infinite-scroll="loadPic"
              :infinite-scroll-disabled="disabledLoadPic"
            >
              <img
                :src="loadingImg(img)"
                v-for="img in imgs"
                class="img"
                @click="inputImg(img)"
              />
            </main>
          </el-card>
          <el-icon @click="showPicFn">
            <Picture />
          </el-icon>
          <el-icon>
            <Coin />
          </el-icon>
        </div>
        <div class="input-wrap">
          <div
            contenteditable="true"
            class="chat-input"
            ref="input"
            @keyup.enter="emitChat"
            @paste="preventPaste($event)"
          ></div>
        </div>
        <el-button class="emit-chat" @click="emitChat">聊天</el-button>
        <br /><br />
      </footer>
    </div>
  </div>
</template>
<script setup lang="ts">
import {
  User,
  Upload,
  Star,
  Picture,
  Coin,
  Close,
} from "@element-plus/icons-vue";
import { ref } from "vue";

import aMsgVue from "./components/aMsg.vue";
import { handleInput, getPos } from "./composable/useInput";
import { useSocketInit } from "./composable/useSocketInit";
import { loadingImg } from "@/utils/loadingImg";

import { useLoginStore } from "@/stores/login";
import { ElMessage } from "element-plus";

import IMG1 from "@/assets/img/1.jpg";

const loginStore = useLoginStore();

const name = loginStore.userData.name;

const props = defineProps({
  id: {
    type: String,
    default: "100a",
  },
});

const hiddenChatRoom = ref(false);

const input = ref();
const showPic = ref(false);
const emitChat = () => {
  if (loginStore.isLogin) {
    const content = getContent();
    if (content) {
      chatHistory.value.push({
        name: name,
        content: content,
        medal: [],
        nameColor: "",
      });
    }
  } else {
    ElMessage.info("未登录不能参与聊天");
  }
};
const preventPaste = (evt: ClipboardEvent) => {
  console.log(evt.clipboardData);
  evt.preventDefault();
  return false;
};

//加载更多表情包
const loadPic = () => {};
// 禁止表情重复加载
const disabledLoadPic = ref(false);
const imgs = ref([IMG1]);

const { inputImg, getContent } = handleInput(input, props);
const { chatHistory } = useSocketInit(props);

const showPicFn = () => {
  showPic.value = !showPic.value;
  getPos(input.value);
};
</script>
<style lang="scss" scoped>
.chat-room {
  position: relative;
  height: calc(100vh - var(--header-height));

  .show-room {
    font-size: 30px;
    font-weight: 600;
    position: absolute;
    top: 80px;
    left: -30px;

    transform: rotate(-90deg);
    color: #fff;
  }

  .icon-active {
    color: #fff;
    opacity: 0;
  }

  .card {
    position: relative;

    height: calc(100vh - var(--header-height));
    width: var(--chat-room-width);
    width: 400px;
    overflow: hidden;

    transition: 0.3s width;

    display: flex;
    flex-direction: column;

    background-color: #1f1f23;

    .header {
      height: 72px;
      box-sizing: border-box;
      padding: 13px 25px;
      display: flex;
      align-items: center;
      justify-content: space-between;

      font-size: 18px;

      font-weight: bold;
      line-height: 15px;
      letter-spacing: 0em;

      color: #ffffff;

      .to-right {
        font-size: 25px;
      }
    }

    main {
      color: #fff;

      height: calc(100vh - var(--header-height) - 150px);

      flex: 1;

      overflow-y: auto;

      padding: 0 25px 136px;

      // padding-bottom: 110px;

      -ms-overflow-style: none;
      overflow: -moz-scrollbars-none;

      &::-webkit-scrollbar {
        width: 0 !important;
      }
    }

    footer {
      height: 136px;
      position: absolute;
      right: 2px;
      bottom: 0px;
      box-sizing: border-box;
      padding: 10px;
      width: var(--chat-room-width);
      color: #fff;
      background-color: #313131;

      .input-wrap {
        height: 90px;
        box-sizing: border-box;
        border: 1px solid #aaa;
        border-radius: 5px;

        overflow-y: scroll;

        -ms-overflow-style: none;
        overflow: -moz-scrollbars-none;

        &::-webkit-scrollbar {
          width: 0 !important;
        }

        .chat-input {
          width: 100%;
          height: 50px;
          box-sizing: border-box;
          padding: 5px 60px 0 40px;

          line-height: 40px;

          margin-bottom: 10px;

          outline: none;
          border: none;

          font-size: 16px;
        }
      }

      .pre,
      .suf {
        font-size: 25px;

        position: absolute;
        top: 22px;
        z-index: 99;
      }

      .pre {
        left: 20px;
      }

      .suf {
        right: 20px;

        // position: relative;
        .show-pic {
          box-sizing: border-box !important;
          position: absolute;
          bottom: 50px;
          right: 1px;
          width: 350px;
          height: 300px;

          background-color: #313131;

          .close-pic {
            position: absolute;
            right: 10px;
            top: 10px;
            color: #fff;
          }

          .img {
            width: 30px;
            height: 30px;
          }
        }
      }

      .emit-chat {
        background-color: var(--btn-bcc);
        color: #fff;

        float: right;
      }
    }

    footer::before {
      content: "";
      display: block;
      clear: both;
    }
  }

  .active {
    width: 0;
  }

  :deep(.el-input__wrapper) {
    padding-left: 38px;
    padding-right: 60px;
  }
}
</style>
