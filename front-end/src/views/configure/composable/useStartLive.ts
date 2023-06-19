import { postStartOrder } from "@/request/config";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";


const startLive = async () => {
    const { data: reqData } = await postStartOrder();
    let resolve,reject;
    if (reqData.code === 500) {
      ElMessage({
        type: "error",
        message: "开播失败，请检查是否成功推流",
      });
      return Promise.reject()
    } else if (reqData.code === 0) {
      ElMessage({
        type: "success",
        message: "开播成功",
      });
      // router.push({ path: '/' })
      return Promise.resolve()
    } else if (reqData.code === 502) {
      ElMessage({
        type: "error",
        message: "开播失败，请先配置直播间信息",
      });
      return Promise.reject()
    }
  };

  export const useStartLive = () => {
    return {startLive}
  }