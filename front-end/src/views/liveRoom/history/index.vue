<template>
  <div>
    <p>Live Recordings</p>
    <div class="line-wr">
      <img src="@/assets/img/1.jpg" alt="" class="history" />
      <img src="@/assets/img/2.jpg" alt="" class="history" />
      <img src="@/assets/img/3.jpg" alt="" class="history" />
      <img src="@/assets/img/4.jpg" alt="" class="history" />
      <img src="@/assets/img/1.jpg" alt="" class="history last" />
    </div>
    <div class="line-wr">
        <img src="@/assets/img/3.jpg" alt="" class="history" />
      <img src="@/assets/img/1.jpg" alt="" class="history" />
      <img src="@/assets/img/2.jpg" alt="" class="history" />
      <img src="@/assets/img/4.jpg" alt="" class="history" />
      <img src="@/assets/img/1.jpg" alt="" class="history last" />
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
p {
  height: 39px;
  font-family: HarmonyOS_Sans_SC_Bold;
  font-size: 15px;
  font-weight: bold;
  line-height: 39px;
  letter-spacing: 0em;

  color: #ffffff;
}
.line-wr {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
.history {
  /* 矩形 15 */
  width: 250px;
  height: 140px;
  opacity: 0.9;
  /* #FFFFFF */
  background: #ffffff;
}
</style>
