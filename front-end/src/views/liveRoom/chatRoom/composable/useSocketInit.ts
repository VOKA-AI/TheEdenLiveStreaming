import { enterRoom } from "@/utils/socket/useSocket";
import { useMainStore } from "@/stores/index";
import { onBeforeRouteLeave, onBeforeRouteUpdate } from "vue-router";
import { ref, onBeforeUnmount } from "vue";
import type { Ref } from "vue";

import bus from "@/bus/bus";

type Msg = {
  type: "img" | "text";
  value: string;
};

type AChatMsg = {
  name: string;
  medal?: Array<any>;
  content: Msg[];
  nameColor?: string;
};

const mainStore = useMainStore();

export function useSocketInit(props: any) {
  const chatHistory: Ref<AChatMsg[]> = ref([]);

  const sendMsglistener = (msg: Array<any>, name: string) => {
    console.log(123);
    chatHistory.value.push({
      name: name as string,
      content: msg,
    });
  };

  const listenSendMsg = () => {
    console.log("listenSendMsg");
    mainStore.socket?.on("sendMsg", sendMsglistener);
  };

  const sendInfoEnterRoom = () => {
    mainStore.socket?.emit("leaveRoom", props.id);
    enterRoom(props.id);
    listenSendMsg();
  };

  if (mainStore.socket !== undefined) {
    sendInfoEnterRoom();
  }
  bus.on("socketConnect", sendInfoEnterRoom);

  onBeforeRouteUpdate(() => {
    enterRoom(props.id);
  });
  onBeforeRouteLeave(() => {
    mainStore.socket?.emit("leaveRoom", props.id);
  });
  onBeforeUnmount(() => {
    mainStore.socket?.off("sendMsg", sendMsglistener);
    bus.off("socketConnect");
  });
  return {
    chatHistory,
  };
}
