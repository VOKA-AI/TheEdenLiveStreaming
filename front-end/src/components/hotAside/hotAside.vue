<template>
  <div class="aside">
    <week class="week"></week>
    <LeaderBoard></LeaderBoard>
  </div>
</template>

<script setup lang="ts">
import LeaderBoard from "./leaderBoard.vue";
import Week from "./week.vue";
</script>

<style scoped lang="scss">
.aside {
  width: 200px;
  height: calc(100vh - var(--header-height));
  background: #1f1f23;
  .week {
    margin-top: 10px;
    margin-bottom: 33px;
  }
}
</style>
