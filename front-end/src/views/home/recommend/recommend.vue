<!-- eslint-disable vue/multi-word-component-names -->
<!-- eslint-disable vue/valid-v-for -->
<!-- eslint-disable vue/require-v-for-key -->
<template>
  <div class="recommend">
    <main>
      <!-- <small-card></small-card> -->
      <large-card
        :card-type="1"
        class="recommend-card"
        v-if="loginStore.isLogin"
      ></large-card>
      <large-card :card-type="0"></large-card>
    </main>
  </div>
</template>

<script setup lang="ts">
// import LargeCard from "@/components/livecard/largeCard.vue";
import LargeCard from "./livecard/largeCard.vue";
import { useLoginStore } from "@/stores/login";
const loginStore = useLoginStore();
</script>

<style lang="scss" scoped>
.recommend {
  box-sizing: border-box;
  overflow: hidden;
  padding: 70px;
}

main {
  padding: 0;
  margin: 0;
}

.recommend-card {
  margin-bottom: 50px;
}
</style>
