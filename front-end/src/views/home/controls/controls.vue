<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="controls">
    <img
      src="@/assets/icons/button_browse_refresh.svg"
      alt=""
      class="reset"
      @click="refresh"
    />
    <img
      src="@/assets/icons/button_browse_forward.svg"
      alt=""
      class="to-left"
      @click="toLeft"
    />
    <img
      src="@/assets/icons/button_browse_next.svg"
      alt=""
      class="to-right"
      @click="toRight()"
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
  //console.log("toRight");
  emit("toRight");
};
</script>

<style lang="scss" scoped>
.controls {
  z-index: 999;
  text-align: right;
  & > img {
    width: 26px;
    height: 26px;
    // background-color: #000;
    margin: 0 10px;
    cursor: pointer;
  }
}
</style>
