/**
 *  使用逻辑：
 *  在app页注册连接socket
 *  连接成功时用事件总线发送连接事件socketConnect
 *  假如用户未登录，则不在header中假如 id和 token，由服务器分配一个id
 *  当用户登录时，断开该连接，重新连接soeket，并将，id和token放在header中
 */

import { getTokenAUTH, removeTokenAUTH } from "@/utils/localStorage";
import { ElMessage } from "element-plus";
import { io } from "socket.io-client";
import type { Socket } from "socket.io-client";
import bus from "@/bus/bus";

const token = getTokenAUTH();
let mainStore: any;
let loginStore: any;

let name: string | undefined;
let id: string | undefined | number;

let socket: undefined | Socket;

const getSocketIOUrl = () => {
    return "http://176.34.17.140:8089";
};

async function initSocket() {
  // console.log("socket");

  if (!mainStore) {
    const { useMainStore } = await import("@/stores/index");
    mainStore = useMainStore();
  }
  if (!loginStore) {
    const { useLoginStore } = await import("@/stores/login");
    loginStore = useLoginStore();
    name = loginStore.userData.name;
    id = loginStore.userData.id;
  }

  
  const socketIOURL = getSocketIOUrl();
  socket = io(socketIOURL, {
    // transports: ["websocket"],
    auth: {
      token: token as string,
      name: name,
      id: id,
    },
  });
  socket?.on("connect", () => {
    //console.log("开始连接");
    //console.log(socket);

    mainStore.socket = socket;
    bus.emit("socketConnect");
  });

  socket?.on("linkError", (reason) => {
    // console.log(1133);
    ElMessage.warning({
      message: reason,
    });
    if (reason === "token过期，请重新登录") {
      loginStore.islogin = false;
      loginStore.clearUserData();
      removeTokenAUTH();
      loginStore.changeShowLoginStatus(true);
    }
  });

  socket?.on("joinRoom", (msg) => {
    //console.log(msg);
  });

  socket?.on("leaveRoom", (msg) => {
    //console.log(msg);
  });

  return socket;
}

const enterRoom = (room: string | number) => {
  //console.log("enterRoom");
  socket?.emit("enterRoom", { id, room });
};

const sendMsg = (msg: string | Array<any>, room: string | number) => {
  socket?.volatile.emit("sendMsg", msg, room);
};

const sendPic = (msg: string | URL) => {
  socket?.emit("sendPic", msg);
};

const leaveRoom = (room: number | string) => {
  socket?.emit("leaveRoom", room);
};

const disconnect = () => {
  socket?.off();
  socket?.disconnect();
};

export { enterRoom, sendMsg, sendPic, initSocket, leaveRoom, disconnect };
