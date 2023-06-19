import { defineStore } from "pinia";
import { ref, type Ref } from "vue";
export interface roomInfo {
  userId: number;
  userPortraitUrl: string | undefined;
  userName: string;
  roomId: number;
  livePath: string;
  ipnsaddress: string;
  ipfshost: string;
  ipfsport: number;
  onlineNumber: number;
  infoId: number;
  title: string;
  introduction: string;
  tags: Array<{
    id: number;
    tag: string;
  }>;
  typeId: number;
}
export interface userInfo {
  id: number;
  name: string;
  nickname: string;
  portrait: string | undefined;
  selfIntroduction: string | null;
  followedNumber: number;
  state: boolean;
}
export const useLiveStore = defineStore(
  "live",
  () => {
    const roomInfo: Ref<roomInfo> = ref({
      userId: -1,
      userPortraitUrl: "",
      userName: "",
      roomId: -1,
      livePath: "",
      ipnsaddress: "/ipns/k51qzi5uqu5dlx3o6263fj29q7q0rnmzy4o4q30hibchdtd5u6myoj0hshmvaz/",
      ipfshost:"35.77.2.79",
      ipfsport:5001,
      onlineNumber: -1,
      infoId: -1,
      title: "",
      introduction: "",
      tags: [],
      typeId: 1,
    });
    const liveTypeId = ref(1);
    const setRoomInfo = (value: roomInfo) => {
      roomInfo.value.userId = value.userId;
      roomInfo.value.userPortraitUrl = value.userPortraitUrl;
      roomInfo.value.userName = value.userName;
      roomInfo.value.roomId = value.roomId;
      roomInfo.value.livePath = value.livePath;
      roomInfo.value.ipnsaddress = value.ipnsaddress;
      roomInfo.value.ipfshost = value.ipfshost;
      roomInfo.value.ipfsport = value.ipfsport;
      roomInfo.value.onlineNumber = value.onlineNumber;
      roomInfo.value.infoId = value.infoId;
      roomInfo.value.title = value.title;
      roomInfo.value.introduction = value.introduction;
      roomInfo.value.tags = value.tags;
      roomInfo.value.typeId = value.typeId;
      setTypeId(value.typeId);
    };
    const getLivePath = () => {
      return roomInfo.value.livePath;
    };
    const isShowConfig = ref(false);
    const setShowConfig = () => {
      isShowConfig.value = !isShowConfig.value;
    };
    const setTypeId = (id: number) => {
      liveTypeId.value = id;
    };

    //用于存储当前主播信息
    const userInfo: Ref<userInfo> = ref({
      id: 1,
      name: "张三",
      nickname: "张三",
      portrait:
        "https://s3.ap-east-1.amazonaws.com/srs-live-web-storage-hk/portrait/1668511789045-default.png",
      selfIntroduction: null,
      followedNumber: 9,
      state: true,
    });
    const setUserInfo = (user: userInfo) => {
      userInfo.value.id = user.id;
      userInfo.value.name = user.name;
      userInfo.value.nickname = user.nickname;
      userInfo.value.portrait = user.portrait;
      userInfo.value.selfIntroduction = user.selfIntroduction;
      userInfo.value.followedNumber = user.followedNumber;
      userInfo.value.state = user.state;
    };

    return {
      roomInfo,
      setRoomInfo,
      getLivePath,
      isShowConfig,
      setShowConfig,
      setTypeId,
      liveTypeId,
      userInfo,
      setUserInfo,
    };
  },
  {
    persist: true,
  }
);
