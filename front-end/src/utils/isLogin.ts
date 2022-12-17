import { getUserData } from "@/request/user";
import { reqJudgeTokenStatus } from "@/request/login";
import { getTokenAUTH } from "./localStorage";
import { initSocket } from "@/utils/socket/useSocket";

export async function isLogin() {
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
        console.log("data.result;");
        console.log(data.result);
        loginstore.userData = {
          id: id,
          name: name,
          portrait: portrait,
        };
        initSocket();
      }
    } catch (error) {
      console.log(error);
    }

  }
}
