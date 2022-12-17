import axios from "axios";
import {
  getTokenAUTH,
  removeStorage,
  removeTokenAUTH,
} from "@/utils/localStorage";
import { ElMessage } from "element-plus";
let loginstore: any;

// 创建axios实例
// 设置请求超时时间

const serve = axios.create({
  baseURL: "/api",
  timeout: 5000,
});

// 设置post请求头
serve.defaults.headers.post["Content-Type"] =
  "application/x-www-form-urlencoded;charset=UTF-8 ";

// 请求拦截器
serve.interceptors.request.use(
  (config) => {
    // 根据本地是否存在token判断用户的登录状况
    // 每次请求都携带token，这个请求是否需要token由后台去判断
    const token = getTokenAUTH();
    token &&
      config.headers &&
      (config.headers.Authorization = "Bearer " + token);
    token && config.headers && (config.headers.token = "Bearer " + token);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const handleError = async (status: number | undefined) => {
  if (!loginstore) {
    const { useLoginStore } = await import("@/stores/login");
    loginstore = useLoginStore();
  }
  switch (status) {
    case 500:
      // ElMessage.error("操作错误");
      break;
    case 506:
      console.log("参数检验失败");
      break;
    case 401:
      // ElMessage.error("token过期");
      removeTokenAUTH();
      removeStorage("userData");
      //loginstore.changeShowLoginStatus(true);
      break;
    case 403:
      ElMessage.error("没有相关权限");
      break;
    default:
      // console.log("成功");
      break;
  }
};

serve.interceptors.response.use((res) => {
  handleError(res.data.code);
  return res;
});
// 导出axios对象
export default serve;
