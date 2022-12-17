import { postStartOrder } from "@/request/config";
import { ElMessage } from "element-plus";

const handleStartLive = async () => {
    const { data: reqData } = await postStartOrder();
    console.log(reqData);
    if (reqData.code === 500) {
      ElMessage({
        type: "error",
        message: "开播失败，请检查是否成功推流",
      });
    } else if (reqData.code === 0) {
      ElMessage({
        type: "success",
        message: "开播成功",
      });
    } else if (reqData.code === 502) {
      ElMessage({
        type: "error",
        message: "开播失败，请先配置直播间信息",
      });
    }
  };

  export const useStartLive = () => {
    return {handleStartLive}
  }