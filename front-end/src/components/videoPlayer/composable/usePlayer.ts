import "xgplayer";
import FlvPlayer from "xgplayer-flv.js";
import { onBeforeUnmount, reactive, inject } from "vue";
import { useLiveStore } from "@/stores/live";

const liveStore = useLiveStore();

let srsURL: string;
const getUrl = () => {
  if (!srsURL) {
    srsURL = inject("srsHost") || "http://176.34.17.140";
  }
  const url = `http://${srsURL}:8080/live/${liveStore.roomInfo.livePath}.flv`;
  return url;
};
export const usePlayer = (contain: string) => {
  let player: {
    [x: string]: any;
    error(error: any): unknown;
    networkState(networkState: any): unknown;
    readyState(readyState: any): unknown;
    on: (arg0: string, arg1: { (error: {}): void; (res: any): void }) => void;
    destroy: () => void;
  };
  const playerInfo = reactive({
    lastDecodedFrame: 0, //最后一个decodedFrame
    sameCount: 0, //decodedFrame不变次数
  });
  const createPlayer = (url: string) => {
    const flvPlayer = new FlvPlayer({
      el: document.querySelector(contain),
      url: url,
      isLive: true,
      // autoplay: true,
      crossOrigin: false,
      fluid: true,
      videoInit: true,
      // videoInit: true,
    });
    player = flvPlayer;
    return flvPlayer;
  };
  const initPlayer = () => {
    const url = getUrl();
    return createPlayer(url);
  };
  player = initPlayer();
  player.on("error", (error: {}) => {
    ////console.log(error, "error");
    // console.log(player.error);
    // console.log(player.networkState);
    // console.log(player.readyState);
    if (player) {
      player.destroy();
    }
    initPlayer();
  });
  //检测画面是否卡死。如果decodedFrames不再发生变化,就销毁掉该实例并进行重新连接。
  player.on("statistics_info", (res: any) => {
    //console.log(player, "resInfo");
    // 首帧
    if (playerInfo.lastDecodedFrame === 0) {
      playerInfo.lastDecodedFrame = res.decodedFrames;
      return;
    }
    //页面有变化
    if (playerInfo.lastDecodedFrame != res.decodedFrame) {
      playerInfo.lastDecodedFrame = res.decodedFrames;
      playerInfo.sameCount = 0;
      // console.log('页面有变化');
      return;
    } else {
      playerInfo.sameCount++;
      //超过10秒不变，判断画面卡死，销毁实例进行重连
      if (playerInfo.sameCount > 9) {
        //console.log("页面卡死");
        playerInfo.lastDecodedFrame = 0;
        playerInfo.sameCount = 0;
        //销毁重连
        player.destroy();
        if (player) {
          initPlayer();
        }
      }
    }
  });
  player.on("requestFullscreen", (res) => {
    //console.log("进入全屏");
  });
  player.on("progress", () => {
    if (player.buffered.length > 0) {
      //获取当前buffer值即缓冲区末尾
      const end = player.buffered.end(0);
      //获取buffer与当前播放位置的差值
      const delta = end - player.currentTime;
      //console.log(delta);
      if (delta > 10 || delta < 0) {
        player.currentTime = player!.buffered!.end(0) - 1;
        return;
      }
      // 追帧
      if (delta > 1) {
        player.playbackRate = 1.1;
      } else {
        player.playbackRate = 1;
      }
    }
    // console.log(player!.buffered.end(0));
  });
  player.on("play", () => {
    if (player.buffered.length > 0) {
      player.currentTime = player!.buffered!.end(0) - 1;
      // console.log(player!.buffered.end(0));
      return;
    }
  });
  // 网页重新激活后，更新视频
  window.onfocus = () => {
    if (player!.buffered && player!.buffered!.length > 0) {
      const end = player!.buffered!.end(0) - 1;
      // console.log(player!.buffered);
      player.currentTime = end;
    }
  };
  onBeforeUnmount(() => {
    if (player) {
      player.destroy();
    }
  });

  return player;
};
