import "xgplayer";
import FlvPlayer from "xgplayer-flv.js";
import { onBeforeUnmount, onMounted, reactive, ref } from "vue";
import { useLiveStore } from "@/stores/live";

// onMounted(() => {
//     try {
//         let player: {
//             on: (arg0: string, arg1: (error: {}) => void) => void;
//             pasuse: () => void;
//         };
//         let playerInfo = reactive({
//             lastDecodedFrame: 0, //最后一个decodedFrame
//             sameCount: 0, //decodedFrame不变次数
//         });
//         const path = `http://18.163.79.28:8080/live/${liveStore.roomInfo.livePath}.flv`;
//         player = new FlvPlayer({
//             el: document.querySelector("#xg"),
//             url: path,
//             isLive: true,
//             // autoplay: true,
//             crossOrigin: false,
//             fluid: true,
//             videoInit: true,
//             // videoInit: true,
//         });
//         player.on("error", (error: {}) => {
//             console.log(error);
//         });
//         player.on("statistics_info", (res: any) => {
//             // 首帧
//             if (playerInfo.lastDecodedFrame === 0) {
//                 playerInfo.lastDecodedFrame = res.lastDecodedFrame
//                 return
//             }
//             //页面有变化
//             if (playerInfo.lastDecodedFrame != res.decodedFrame) {
//                 playerInfo.lastDecodedFrame = res.lastDecodedFrame
//                 playerInfo.sameCount = 0
//                 return
//             } else {
//                 playerInfo.sameCount++
//                 //超过10秒不变，判断画面卡死，销毁实例进行重连
//                 if (playerInfo.sameCount > 9) {
//                     playerInfo.lastDecodedFrame = 0
//                     playerInfo.sameCount = 0
//                     //销毁重连
//                 }
//             }
//         })
//     } catch (error) { }
// });
const liveStore = useLiveStore();
let getUrl = ()=>{
    const url = `http://18.163.79.28:8080/live/${liveStore.roomInfo.livePath}.flv`;
    return url
}
export const usePlayer = (contain: string) => {
    let player: {
        error(error: any): unknown;
        networkState(networkState: any): unknown;
        readyState(readyState: any): unknown; on: (arg0: string, arg1: { (error: {}): void; (res: any): void; }) => void; destroy: () => void; 
}
    let playerInfo = reactive({
        lastDecodedFrame: 0, //最后一个decodedFrame
        sameCount: 0, //decodedFrame不变次数
    });
    const createPlayer = (url:string)=>{
        if(player){
            player.destroy()
        }
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
        player= flvPlayer
        return flvPlayer
    }
    const initPlayer = ()=>{
        const url = getUrl()
        return createPlayer(url)
    }
    player = initPlayer()
    player.on("error", (error: {}) => {
        console.log(error,'error');
        console.log(player.error);
        console.log(player.networkState);
        console.log(player.readyState);
    });
    player.on("statistics_info", (res: any) => {
        console.log(res,'resInfo');
        // 首帧
        if (playerInfo.lastDecodedFrame === 0) {
            playerInfo.lastDecodedFrame = res.decodedFrames
            return
        }
        //页面有变化
        if (playerInfo.lastDecodedFrame != res.decodedFrame) {
            playerInfo.lastDecodedFrame = res.decodedFrames
            playerInfo.sameCount = 0
            // console.log('页面有变化');
            return
        } else {
            
            playerInfo.sameCount++
            //超过10秒不变，判断画面卡死，销毁实例进行重连
            if (playerInfo.sameCount > 9) {
                console.log('页面卡死');
                playerInfo.lastDecodedFrame = 0
                playerInfo.sameCount = 0
                //销毁重连
                if(player){
                    initPlayer()
                }
            }
        }
    })
    player.on("requestFullscreen",(res)=>{
        console.log('进入全屏');
    })
    // player.on(FlvPlayer.Events.Error,(error)=>{
    //     console.log(error,123);
    // })

    onBeforeUnmount(()=>{
        if(player){
            player.destroy()
        }
    })

    return player
}