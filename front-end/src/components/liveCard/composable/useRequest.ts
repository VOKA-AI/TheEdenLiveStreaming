/* eslint-disable no-debugger */
import { reqGetRoomByType } from "@/request/category";
import { getRecording } from "@/request/user";

import { ref, reactive, watch } from "vue";
import type { Ref } from "vue";
import type { LiveCard, PageConfig } from "../interface/interface";
import { ElMessage } from "element-plus";

const reqFn = (type: "0" | "1") => {
  return function (id: number, num: number, size: number) {
    return type === "0"
      ? reqGetRoomByType({ typeId: id, num, size })
      : getRecording({ userId: id, num, size });
  };
};

export const useRequest = (type: "0" | "1", props: any) => {
  const isActive = ref(true);
  const pageConfig: PageConfig = reactive({
    pageSize: 20,
    total: 20,
    currentPage: 0,
  });

  const cardData: Ref<LiveCard[]> = ref([]);
  const fn = reqFn(type);

  const updateData = async () => {
    const { data } = await fn(
      props.typeOrUserId,
      pageConfig.currentPage,
      pageConfig.pageSize
    );
    console.log(data.code);
    if (data.code === 0) {
      pageConfig.total = data.result.total;
      cardData.value.length = 0;
      console.log(data.result.records);
      cardData.value.push(...data.result.records);
      console.log("onlineNumber", cardData.value[0]);
    } else {
      // console.log(data);
      ElMessage.warning(data.message)
    }
  };

  watch(
    () => pageConfig.currentPage,
    () => {
      console.log("updateData");
      updateData();
    }
  );

  watch(
    () => props.typeOrUserId,
    () => {
      console.log("updateData1");
      if (pageConfig.currentPage === 1) {
        updateData();
      } else {
        pageConfig.currentPage = 1;
      }
    }
  );

  pageConfig.currentPage++;
  return {
    updateData,
    cardData,
    pageConfig,
    isActive,
  };
};
