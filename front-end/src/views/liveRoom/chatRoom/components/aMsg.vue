<!-- eslint-disable vue/require-v-for-key -->
<template>
  <div class="a-msg">
    <i v-for="item in medal"> {{ item }} </i>
    <strong class="name">{{ props.name }}</strong
    >:
    <div class="content">
      <div tyle="display: inline-block">
        <!-- <img
          :src="item.value"
          alt=""
          v-if="item.type === 'img'"
          class="img-text"
        /> -->
        <span class="text">{{ content }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
type Msg = {
  type: "img" | "text";
  value: string;
};

const props = withDefaults(
  defineProps<{
    name?: string;
    nameColor?: string;
    content: String;
    medal?: Array<any> | undefined;
  }>(),
  {
    nameColor: "#fff",
  }
);
</script>

<style lang="scss" scoped>
.a-msg {
  display: flex;

  .name {
    display: block;
    font-size: 20px;
    // min-width: 100px;
    max-width: 200px;
    color: v-bind("props.nameColor");
    text-overflow: ellipsis;
    // overflow: hidden;
  }
  .content {
    font-size: 18px;
    padding-left: 10px;
    overflow: hidden;
    // white-space: break-all;
    white-space: pre-line;
    word-break: break-word;
    text-align: left;
    .img-text {
      width: 30px;
      height: 30px;
      border-radius: 3px;
      vertical-align: middle;
    }
    .text {
      vertical-align: middle;
    }
  }
}
</style>
