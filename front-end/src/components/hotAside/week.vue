<template>
  <div class="hot-wr">
    <h3># 当前分区直播综合周榜</h3>
    <div class="order" v-for="(n, index) in 5" :key="n">
      <img :src="imgUrl[index]" alt="" />
      <div class="hot">
        <div class="l">
          <img src="@/assets/img/1.jpg" alt="" class="avator" />
        </div>
        <div class="r">
          <p class="name">aaasssssssssssss</p>
          <p class="num">
            <img src="@/assets/icons/Icon_Media_Heat.svg" alt="" />
            2.9万播放量
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import IMG1 from "@/assets/icons/Icon_Media_Medal_Golden.svg";
import IMG2 from "@/assets/icons/Icon_Media_Medal_Golden.svg";
import IMG3 from "@/assets/icons/Icon_Media_Medal_Bronze.svg";

const imgUrl: string[] = [IMG1, IMG2, IMG3, IMG3, IMG3];
</script>

<style scoped lang="scss">
.hot-wr {
  /* 矩形 12 */
  width: 200px;
  box-sizing: border-box;
  padding-left: var(--left-padding-l);
  padding-right: var(--left-padding-r);
  opacity: 1;
  h3 {
    /* # 当前分区直播综合周榜 */
    height: 15px;
    opacity: 1;
    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 15px;
    font-weight: bold;
    line-height: 15px;
    letter-spacing: 0em;
    color: #ffffff;
    margin-bottom: 17px;
  }
  .order {
    display: flex;
    justify-content: space-between;
    width: 200px;
    margin-bottom: 15px;

    .hot {
      width: 140px;
      display: flex;
      justify-content: space-between;
      .l {
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background-color: #ffffff;
        img {
          width: 30px;
          height: 30px;
          border-radius: 50%;
        }
      }
      .r {
        width: 100px;
        .avator {
          margin-right: 20px;
          vertical-align: center;
        }
        .name {
          /* 主播名###若干字符 */
          width: 80px;
          margin-bottom: 4px;

          opacity: 1;
          font-family: HarmonyOS_Sans_Light;
          font-size: 12px;
          font-weight: 300;
          line-height: 12px;
          letter-spacing: 0em;
          color: #ffffff;
          overflow: hidden; //超出的文本隐藏
          text-overflow: ellipsis; //溢出用省略号显示
          white-space: nowrap; //溢出不换行
        }
        .num {
          width: 80px;
          font-family: HarmonyOS_Sans_Light;
          font-size: 12px;
          font-weight: 300;
          line-height: 12px;
          letter-spacing: 0em;
          color: #ffffff;
          overflow: hidden; //超出的文本隐藏
          text-overflow: ellipsis; //溢出用省略号显示
          white-space: nowrap; //溢出不换行
          img{
            vertical-align: text-bottom;
          }
        }
      }
    }
  }
}
</style>
