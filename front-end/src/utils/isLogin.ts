import { getUserData } from "@/request/user";
import { reqJudgeTokenStatus } from "@/request/login";
import { getTokenAUTH } from "./localStorage";
import { initSocket } from "@/utils/socket/useSocket";
import type { Router, RouteLocationNormalizedLoaded } from "vue-router";

export async function isLogin(
  router?: Router,
  route?: RouteLocationNormalizedLoaded
) {
  //console.log(route?.path);
  //console.log(router);
  const { useLoginStore } = await import("@/stores/login");
  const loginstore = useLoginStore();
  if (getTokenAUTH() !== null) {
    try {
      const { data } = await reqJudgeTokenStatus();
      if (data.code !== 0) {
        loginstore.isLogin = false;
      } else {
        loginstore.isLogin = true;
        const { data } = await getUserData();
        const { id, name, portrait } = data.result;
        //console.log("data.result;");
        //console.log(data.result);
        loginstore.userData = {
          id: id,
          name: name,
          portrait: portrait,
        };
        import("vue-router").then(({ useRoute }) => {
          //console.log(useRoute());
        });
        initSocket();
      }
    } catch (error) {
      console.log(error);
    }
  }
}

export function a() {
  isLogin();
}
