<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="square">
    <carousel-chart></carousel-chart>
    <recommend></recommend>
    <Footer class="footer"></Footer>
  </div>
</template>

<script setup lang="ts">
import CarouselChart from "./carouselChart/carouselChart.vue";
import Footer from "./footer/footer.vue";
import Recommend from "./recommend/recommend.vue";
</script>

<style scoped lang="scss">
.square {
  padding-top: var(--header-height);
  box-sizing: border-box;
  overflow-y: auto;
  height: calc(100vh - var(--header-height));
  -ms-overflow-style: none;
  overflow: -moz-scrollbars-none;
  &::-webkit-scrollbar {
    width: 0 !important;
  }
  .footer {
    position: fixed;
    bottom: 0;
    left: 0;
  }
}
</style>
