<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="controls">
    <img src="@/assets/icons/Button_Refresh.svg" alt="" class="reset" @click="refresh" />
    <img
      src="@/assets/icons/Button_Backward.svg"
      alt=""
      class="to-left"
      @click="toLeft"
    />
    <img
      src="@/assets/icons/Button_Forward.svg"
      alt=""
      class="to-left"
      @click="toRight"
    />
  </div>
</template>

<script setup lang="ts">
const emit = defineEmits(["refresh", "toLeft", "toRight"]);
const refresh = () => {
  emit("refresh");
};

const toLeft = () => {
  emit("toLeft");
};

const toRight = () => {
  emit("toRight");
};
</script>

<style lang="scss" scoped>
.controls {
  text-align: right;
  & > img {
    width: 26px;
    height: 26px;
    background-color: #000;
    margin: 0 10px;
    cursor: pointer;
  }
}
</style>
