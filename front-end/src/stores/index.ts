import { defineStore } from "pinia";
import { ref } from "vue";
import type { Ref } from "vue";
import type { Socket } from "socket.io-client";

export const useMainStore = defineStore("main", () => {
  const socket: Ref<Socket | undefined> = ref();
  return {
    socket,
  };
});
