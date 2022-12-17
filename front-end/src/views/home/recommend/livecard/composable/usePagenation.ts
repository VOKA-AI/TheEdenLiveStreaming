import { reactive, ref } from "vue";
import type { Ref } from "vue";
import type { LiveCard } from "../interface/interface";

import { reqGetOnlinRooms, reqGetAllOnlinRoomsFollowed } from "@/request/home";

import IMG4 from "@/assets/img/4.jpg";
import IMG2 from "@/assets/img/2.jpg";

const getReqType = (type: 0 | 1) => {
  switch (type) {
    case 0:
      return reqGetOnlinRooms;
    case 1:
      return reqGetAllOnlinRoomsFollowed;
  }
};

export const usePagenation = (type: 0 | 1) => {
  // 分页功能
  const pageConfig = reactive({
    currentPage: 1,
    pagesize: 5,
    isLast: false,
    totalPage: 1,
  });

  const liveCard: Ref<LiveCard[]> = ref([
    {
      userId: 1,
      onlineNumber: 100,
      coverUrl: IMG4,
      userPortraitUrl: IMG2,
      title: "按时发放",
      userName: "撒旦发生发生发",
      introduction: "地方撒发射点发顺丰发",
      livePath: "",
      tags: [
        {
          id: 1,
          tag: "标签",
        },
      ],
      roomId: 1,
      infoId: 1,
    },
    {
      userId: 1,
      onlineNumber: 100,
      coverUrl: IMG4,
      userPortraitUrl: IMG2,
      title: "按时发放",
      userName: "撒旦发生发生发",
      introduction: "地方撒发射点发顺丰发",
      livePath: "",
      tags: [
        {
          id: 1,
          tag: "标签",
        },
      ],
      roomId: 1,
      infoId: 1,
    },
    {
      userId: 1,
      onlineNumber: 100,
      coverUrl: IMG4,
      userPortraitUrl: IMG2,
      title: "按时发放",
      userName: "撒旦发生发生发",
      introduction: "地方撒发射点发顺丰发",
      livePath: "",
      tags: [
        {
          id: 1,
          tag: "标签",
        },
      ],
      roomId: 1,
      infoId: 1,
    },
    {
      userId: 1,
      onlineNumber: 100,
      coverUrl: IMG4,
      userPortraitUrl: IMG2,
      title: "按时发放",
      userName: "撒旦发生发生发",
      introduction: "地方撒发射点发顺丰发",
      livePath: "",
      tags: [
        {
          id: 1,
          tag: "标签",
        },
      ],
      roomId: 1,
      infoId: 1,
    },
    {
      userId: 1,
      onlineNumber: 100,
      coverUrl: IMG4,
      userPortraitUrl: IMG2,
      title: "按时发放",
      userName: "撒旦发生发生发",
      introduction: "地方撒发射点发顺丰发",
      livePath: "",
      tags: [
        {
          id: 1,
          tag: "标签",
        },
      ],
      roomId: 1,
      infoId: 1,
    },
  ]);

  const isShow = ref(true);

  const reqFn = getReqType(type);

  const refreshOrPush = async (flag: boolean) => {
    if (flag) {
      pageConfig.currentPage = 1;
      pageConfig.isLast = false;
      liveCard.value.length = 0;
      pageConfig.totalPage = 1;
    } else {
      pageConfig.currentPage++;
    }
    const { data } = await reqFn(pageConfig.currentPage, pageConfig.pagesize);
    if (data.code === 0) {
      const result: LiveCard[] = data.result.records;
      if (flag && type === 1 && result.length === 0) {
        isShow.value = false;
      }
      if (result.length < pageConfig.pagesize) {
        pageConfig.isLast = true;
      }
      liveCard.value.push(...result);
    }
  };

  const toLeft = () => {
    if (pageConfig.currentPage > 1) pageConfig.currentPage--;
  };
  const toRight = () => {
    if (pageConfig.currentPage >= pageConfig.totalPage) {
      if (!pageConfig.isLast) {
        refreshOrPush(false);
        pageConfig.totalPage++;
      }
    } else {
      pageConfig.currentPage++;
    }
  };
  const refresh = () => {
    refreshOrPush(true);
  };
  return {
    toLeft,
    refresh,
    toRight,
    pageConfig,
    liveCard,
    isShow,
  };
};
