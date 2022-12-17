<template>
  <div class="card">
    <div class="left">
      <div class="pic">
        <img src="@/assets/logo.svg" alt="" />
      </div>
      <div class="intro">
        <p class="name">tarik</p>
        <p class="info">Ayo|Twiter@tarik</p>
        <p class="tag">DOTA</p>
        <p class="tagGroup">
          <span class="tagInfo">China</span><span class="tagInfo">Dota</span>
        </p>
      </div>
    </div>
    <div class="right">
      <p>
        <button class="btn">关注</button>
        <button class="btn">订阅</button>
      </p>
      <p>
        <span class="people">1,639</span>
        <span class="time">4:20:44</span>
      </p>
      <p></p>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped lang="scss">
.card {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}
.left {
  display: flex;
  flex-direction: row;
  .pic {
    width: 80px;
    height: 80px;
    line-height: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    border: 3px solid #000;
    margin-right: 5px;
    img {
      width: 70%;
      height: 70%;
      margin: 0 auto;
    }
  }
  .name {
    font-size: 28px;
    font-weight: 600;
  }
  .info,
  .tag {
    font-size: 23px;
    font-weight: 600;
  }
  .tag {
    color: #5c16c5;
  }
  .tagInfo {
    background-color: #ddd;
    line-height: 30px;
    padding: 3px 6px;
    margin: 0 0 0 8px;
    border-radius: 12px;
  }
}
.right {
  .btn {
    width: 110px;
    height: 50px;
    margin: 5px;

    font-size: 25px;
    font-weight: 600;
    outline: none;
    border: 0;
    color: #000;
    background-color: #eaeaeb;
    border-radius: 4px;
  }
  .btn:first-of-type {
    color: #fff;
    background-color: #9147ff;
  }

  .time,
  .people {
    margin: 5px;
  }
}
</style>
