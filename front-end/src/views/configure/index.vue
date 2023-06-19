<!-- eslint-disable vue/multi-word-component-names -->
<!-- eslint-disable vue/require-v-for-key -->
<template>
  <div class="configwr">
    <div class="configure">
      <el-card>
        <h2>
          <span>Edit Information For Your Streaming </span>
          <span @click="liveStorse.setShowConfig()"
            ><img src="@/assets/icons/Button_TagClose.svg" alt=""
          /></span>
        </h2>
        <el-form
          label-position="top"
          :model="formVal"
          label-width="130px"
          :rules="rules"
          ref="formRef"
          hide-required-asterisk
        >
          <el-form-item label="Streaming Title" prop="title" class="item">
            <el-input v-model="formVal.title" />
          </el-form-item>
          <el-form-item
            label="Streaming Introduction"
            prop="introduction"
            class="item"
          >
            <el-input v-model="formVal.introduction" />
          </el-form-item>
          <el-form-item label="Sort" prop="type" class="item">
            <el-select
              v-model="formVal.type"
              class="m-2"
              placeholder="Select"
              size="large"
              @change="handleSelectChange()"
            >
              <el-option
                v-for="item in options"
                :key="item.typeId"
                :label="item.type"
                :value="item.type"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Tags" class="item tag">
            <el-input v-model="formVal.tag" @keyup.enter.native="addTag" />
            <el-button @click="addTag">Add</el-button>
          </el-form-item>
          <p class="tagwr">
            <span v-for="(item, index) in formVal.tags" class="taginfo">
              {{ item }}
              <el-icon @click="delTag(index)"><Close /></el-icon>
            </span>
          </p>
          <el-form-item label="Streaming Posters" class="item uploaditem">
            <Upload></Upload>
          </el-form-item>
          <el-form-item class="btnwr item">
            <el-button @click="handleCancel">Cancel</el-button>
            <el-button @click="handleEmit(formRef)" class="colorbgc"
              >Done</el-button
            >
          </el-form-item>
          <!-- 分割线 -->
          <el-divider border-style="dashed" />
          <el-form-item label="Streaming Url" class="item key">
            <el-input id="address" v-model="serverAddress" :readonly="true" />
            <el-button @click="handleCopy('#address')" class="colorbgc">
              Copy
            </el-button>
          </el-form-item>
          <el-form-item label="Special Key" class="item key">
            <el-input
              v-model="keyWord"
              type="password"
              :show-password="true"
              id="keyWord"
            />
            <div>
              <el-button @click="handleCopyKeyword" class="colorbgc"
                >Copy</el-button
              >
              <el-button @click="handleReset">Reset</el-button>
            </div>
          </el-form-item>
        </el-form>
        <div class="center">
          <el-button class="colorbgc" @click="handleStartLive">Start</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import Upload from "@/components/upload/upload.vue";
import { onMounted, ref, inject } from "vue";
import { Close } from "@element-plus/icons-vue";
import type { FormInstance } from "element-plus";
import { postCreateRoom } from "@/request/config";
import { useLiveStore } from "@/stores/live";
import { useCopy } from "./composable/useCopy";
import { useConfigureRoom } from "./composable/useConfigureRoom";
import { useStartLive } from "./composable/useStartLive";
import { useSelect } from "./composable/useSelect";
import { useKeyword } from "./composable/useKeyword";
import { useRouter } from "vue-router";
const liveStorse = useLiveStore();
const router = useRouter();
const serverAddress = ref("rtmp://" + inject("srsHost") +  "/live");
const { handleCopy, handleCopyKeyword } = useCopy();
const { formVal, rules, addTag, delTag, emitForm } = useConfigureRoom();
const { startLive } = useStartLive();
const { options, handleSelectChange } = useSelect();
const { keyWord, handleReset } = useKeyword();
let value = ref();
let handleStartLive = () => {
  const promise = startLive();
  promise.then(() => {
    router.push("/");
  });
  liveStorse.setShowConfig();
};
const handleEmit = (formRef: FormInstance | undefined) => {
  const promise = emitForm(formRef);
  promise.then(() => {
    router.push("/");
    liveStorse.setShowConfig();
  });
};
const handleCancel = () => {
  liveStorse.setShowConfig();
};
onMounted(async () => {
  const { data: createRoomReq } = await postCreateRoom();
});

const formRef = ref<FormInstance>();
</script>

<style scoped lang="scss">
.configwr {
  width: 900px;
  height: 788px;
  z-index: 150;
  overflow-y: hidden;
}
.configure {
  /* 矩形 52 */
  width: 900px;
  height: 788px;
  border-radius: 4px;
  opacity: 1;
  background: #1f1f23;
  .el-card {
    height: 788px;
    border-radius: 4px;
    opacity: 1;
    border: 0;
    background: #1f1f23;
    box-sizing: border-box;
    padding: 40px 22px 43px;
    overflow-y: scroll;
  }
  .el-card::-webkit-scrollbar {
    width: 0 !important;
  }
  h2 {
    /* 编辑您的直播信息 */
    width: 813px;
    height: 25px;
    display: flex;
    justify-content: space-between;
    margin-bottom: 26px;
    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 19px;
    font-weight: bold;
    line-height: 25px;
    letter-spacing: 0em;
    color: #ffffff;
    img {
      width: 25px;
      height: 25px;
    }
  }
}
:deep(.item .el-form-item__label) {
  /* 直播标题 */

  margin-bottom: 12px;
  font-family: HarmonyOS_Sans_SC_Bold;
  font-size: 15px;
  font-weight: bold;
  line-height: 15px;
  letter-spacing: 0em;
  color: #ffffff;
}
:deep(.item .el-input__wrapper) {
  background: #494949;

  box-shadow: inset 0px 3px 6px 0px rgba(0, 0, 0, 0.16);
}
:deep(.item .el-input__inner) {
  /* 输入后按回车键创建标签 */

  font-family: HarmonyOS_Sans_SC;
  font-size: 15px;
  font-weight: normal;
  line-height: 15px;
  letter-spacing: 0em;
  color: #ffffff;
}
:deep(.tag .el-form-item__content) {
  width: 813px;
  display: flex;
  justify-content: space-between;
  .el-input {
    width: 703px;
  }
  .el-button {
    width: 100px;
    background: #494949;
    border: 0;
    color: #ffffff;
  }
}
:deep(.key .el-form-item__content) {
  width: 813px;
  display: flex;
  justify-content: space-between;
  .el-input {
    width: 300px;
  }
  .el-button {
    width: 64px;
    height: 42px;
    border-radius: 4px;
    opacity: 1;
    background: #494949;
    box-shadow: inset 0px 3px 6px 0px rgba(0, 0, 0, 0.16);
    border: 0;
    color: #ffffff;
  }
  .colorbgc {
    background: linear-gradient(123deg, #40e3ff 0%, #f548f8 100%);
  }
  .avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
:deep(.btnwr .el-form-item__content) {
  display: flex;
  justify-content: flex-end;
  .el-button {
    width: 100px;
    height: 42px;
    border-radius: 4px;
    opacity: 1;
    color: #ffffff;
    background-color: #1f1f23;
    border: 0;
  }
  .colorbgc {
    background: linear-gradient(123deg, #40e3ff 0%, #f548f8 100%);
  }
}
.center {
  display: flex;
  justify-content: center;
}
.colorbgc {
  background: linear-gradient(123deg, #40e3ff 0%, #f548f8 100%);
}
.center .el-button {
  color: #fff;
  border-radius: 4px;
  border: 0;
}
.tagwr {
  margin-bottom: 20px;
}
.taginfo {
  display: inline-block;
  height: 24px;
  line-height: 24px;
  padding: 4px;
  margin-bottom: 10px;
  margin-right: 4px;
  border-radius: 9px;
  opacity: 1;
  background: #494949;
  font-size: 15px;
  color: #fff;
}
.configwr {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(128, 128, 128, 0.01);
  backdrop-filter: blur(60px);
}
.avatar-uploader .avatar {
  width: 58px;
  height: 58px;
  display: block;
}
:deep(.uploaditem .item .el-form-item__label) {
  margin-top: 6px;
}
</style>
