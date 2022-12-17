import { defineStore } from "pinia";
import { ref, type Ref } from "vue";
export interface roomInfo {
  userId: number;
  userPortraitUrl: string|undefined;
  userName: string;
  roomId: number;
  livePath: string;
  onlineNumber: number;
  infoId: number;
  title: string;
  introduction: string;
  tags: Array<{
    id: number;
    tag: string;
  }>;
  typeId:number
}
export const useLiveStore = defineStore("live", 
() => {
  const roomInfo: Ref<roomInfo> = ref({
    userId: -1,
    userPortraitUrl: '',
    userName: "",
    roomId: -1,
    livePath: "",
    onlineNumber: -1,
    infoId: -1,
    title: "",
    introduction: "",
    tags: [],
    typeId:1
  });
  const liveTypeId = ref(1)
  const setRoomInfo = (value: roomInfo) => {
    roomInfo.value.userId = value.userId;
    roomInfo.value.userPortraitUrl = value.userPortraitUrl;
    roomInfo.value.userName = value.userName;
    roomInfo.value.roomId = value.roomId;
    roomInfo.value.livePath = value.livePath;
    roomInfo.value.onlineNumber = value.onlineNumber;
    roomInfo.value.infoId = value.infoId;
    roomInfo.value.title = value.title;
    roomInfo.value.introduction = value.introduction;
    roomInfo.value.tags = value.tags;
    roomInfo.value.typeId = value.typeId;
    setTypeId(value.typeId)
  };
  const getLivePath = () => {
    return roomInfo.value.livePath;
  };
  const isShowConfig = ref(false);
  const setShowConfig = () => {
    isShowConfig.value = !isShowConfig.value;
  };
  const setTypeId = (id:number)=>{
    liveTypeId.value =  id
  }
  return {
    roomInfo,
    setRoomInfo,
    getLivePath,
    isShowConfig,
    setShowConfig,
    setTypeId,
    liveTypeId
  };
},
{
  persist: true,
},
);
