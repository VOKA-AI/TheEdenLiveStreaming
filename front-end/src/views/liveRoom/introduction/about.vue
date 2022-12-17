<template>
  <el-card >
  <div class="about">
    <div class="left">
      <p class="name">关于 <span id="name">dota2</span></p>
      <p class="focus"><span class="num">12.2万</span>位关注者</p>
      <p class="introduce">Csgo Player</p>
    </div>
    <div class="right">
        <p class="link"><i ><img src="@/assets/logo.svg" alt=""></i>Twiter</p>
        <p class="link"><i ><img src="@/assets/logo.svg" alt=""></i>Twiter</p>
        <p class="link"><i ><img src="@/assets/logo.svg" alt=""></i>Twiter</p>
    </div>
</div>
  </el-card>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
.about {
    width: 100%;
  display: flex;
  justify-content: space-between;
 
}

.name {
  font-size: 42px;
  font-weight: 600;
}
.focus {
    color: #53535F;
    font-size: 26px;
  .num {
    font-size: 26px;
    font-weight: 700;
    padding-right: 5px;
  }
}
.introduce{
    font-size: 22px;
}
.link{
    margin-bottom: 5px;
    color:#4D4D59;
    font-size: 20px;
}
.link i{
    width: 24px;
    height: 24px;
    line-height: 24px;
    margin-right: 8px;
    img{
        width: 24px;
        height: 24px;
        vertical-align: middle;
    }
}
</style>
