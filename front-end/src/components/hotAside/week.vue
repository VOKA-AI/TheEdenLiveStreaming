<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="hot-wr">
    <h3>Weekly Board</h3>
    <div class="order" v-for="(n, index) in 5" :key="n">
      <img class="badge" :src="imgUrl[index]" alt="" />
      <div class="hot">
        <div class="l">
          <img :src="avatarUrl[index]" alt="" class="avator" />
        </div>
        <div class="r">
          <p class="name">{{ nickname[index] }}</p>
          <p class="num">
            <img src="@/assets/icons/Icon_Media_Heat.svg" alt="" />
            {{ hotNum[index] }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import IMG1 from "@/assets/icons/icon_board_golden.svg";
import IMG2 from "@/assets/icons/icon_board_silver.svg";
import IMG3 from "@/assets/icons/icon_board_copper.svg";
import IMG4 from "@/assets/icons/icon_board_diamond.svg";
import avatar1 from "@/assets/img/1.jpg";

const imgUrl: string[] = [IMG1, IMG2, IMG3, IMG3, IMG3];
const avatarUrl: string[] = [avatar1, 
                             "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0zvbuXrUg4SEY-LQNXDIOnBEu8ITDWHTTQ&usqp=CAU",
                             "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyZ_2y9pXptfynfE2DFFGGBLRF9yIjR1muTw&usqp=CAU",
                             "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEQ_j5F2YU70rfSWDakm3zQlpPgYvL7CIb6g&usqp=CAU",
                             "https://occ-0-444-325.1.nflxso.net/dnm/api/v6/9pS1daC2n6UGc3dUogvWIPMR_OU/AAAABZwflc6_p5rzhPDlFU1E7c5uFQV3tkaGj80pfFYhdJB9-Nn9BZFfwq4epyABjXuIygb4NsADajOiKafNvQWmHq6Q8324344XQlNasFPFAeTHcCmlWAqB9GcG.jpg?r=7e4",
                             ];
const nickname: string[] = ["Tom","Jerry","Trump","Musk", "Joe"];
const hotNum: string[] = ["290M","200M", "90M", "80M", "20M"];
</script>

<style scoped lang="scss">
.hot-wr {
  /* 矩形 12 */
  width: var(--home-left-side-width);
  box-sizing: border-box;
  padding-left: var(--left-padding-l);
  padding-right: var(--left-padding-r);
  opacity: 1;
  h3 {
    /* # 当前分区直播综合周榜 */
    height: 15px;
    opacity: 1;
    /* Weekly Board */

  
    opacity: 1;
    font-family: HarmonyOS_Sans_SC_Bold;
    font-size: 15px;
    font-weight: bold;
    line-height: 15px;
    text-transform: uppercase;
    letter-spacing: 0em;
    color: #ffffff;
    margin-bottom: 17px;
  }
  .order {
    display: flex;
    justify-content: left;
    width: var(--home-left-side-width);
    margin-bottom: 15px;
    .badge {
width: 30px;
      height: 30px;
      object-fit: contain;
      padding-right: 10px;
    }
    .hot {
      width: 140px;
      display: flex;
      justify-content: space-between;
      .l {
        width: 30px;
        height: 30px;
        border-radius: 50%;
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
          // line-height: 12px;
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
          // line-height: 12px;
          letter-spacing: 0em;
          color: #ffffff;
          overflow: hidden; //超出的文本隐藏
          text-overflow: ellipsis; //溢出用省略号显示
          white-space: nowrap; //溢出不换行
          img {
            vertical-align: text-bottom;
          }
        }
      }
    }
  }
}
</style>
