import { createRouter, createWebHistory } from "vue-router";
import Home from "@/views/home/index.vue";
import { getLiveRoom, getRoomState } from "@/request/config";
import { ElMessage } from "element-plus";
import { useLiveStore } from "@/stores/live";
let liveStore: any;
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      redirect: "/u/home",
    },
    {
      name: "home",
      path: "/:id?/Home",
      components: {
        left: () => import("@/components/hotAside/hotAside.vue"),
        default: Home,
      },
    },
    {
      name: "user",
      path: "/:id/User/:userId(\\d+)",
      components: {
        left: () => import("../views/user/leftAside/LeftAside.vue"),
        default: () => import("../views/user/index.vue"),
        right: () => import("../views/user/leftAside/LeftAside.vue"),
      },
      beforeEnter(to, from, next) {
        if (!to.params.userId) {
          console.log("缺少userId参数");
          next({ path: "/" });
        } else {
          next();
        }
      },
    },
    {
      path: "/:id",
      name: "liveRoom",
      components: {
        left: () => import("@/components/hotAside/hotAside.vue"),
        default: () => import("@/views/liveRoom/index.vue"),
        right: () => import("@/views/liveRoom/chatRoom/chatRoom.vue"),
      },
      //拦截，若无这个直播间则直接不给跳转，并进行消息提示
      //若有，请求这个直播间的数据然后同步在pinia里面
      beforeEnter: async (to, from) => {
        // reject the navigation
        console.log(to, from);
        if (to.params.id) {
          const req: any = await getLiveRoom(to.params.id as string);
          console.log(req, 6666);
          if (req.data.code === 500) {
            ElMessage({
              message: "搜索的直播间不存在，请重新搜索.",
              type: "error",
            });
            return { path: "/" };
          } else if (req.data.code === 0 && req.data.result.livePath !== "") {
            const { data } = await getRoomState(req.data.result.roomId);
            const val = req!.data!.result;
            if (!liveStore) {
              liveStore = useLiveStore();
            }
            liveStore.setRoomInfo(val);
            console.log(data, "roomsatte");
            if (data.result === 1) {
              // 正在直播

              return true;
            } else {
              ElMessage.success("主播不在直播");
              return {
                name: "user",
                params: {
                  id: "u",
                  userId: req.data.result.userId.toString(),
                },
              };
            }
          }

          return true;
        }
      },
      props: {
        right: true,
      },
    },
    {
      path: "/:id/PlayRoom",
      components: {
        left: () => import("@/views/commentAside/index.vue"),
        default: () => import("@/views/videoPage/index.vue"),
        right: () => import("@/views/recommendAside/index.vue"),
      },
    },
    {
      path: "/:id/configure",
      component: () => import("@/views/configure/index.vue"),
    },
    {
      path: "/:id/category/:typeId(\\d+)",
      name: "category",
      components: {
        left: () => import("@/components/hotAside/hotAside.vue"),
        default: () => import("@/views/category/Index.vue"),
      },
      beforeEnter(to, from, next) {
        if (!to.params.typeId) {
          console.log("缺少typeId参数");
          next({ path: "/" });
        } else {
          if (!liveStore) {
            liveStore = useLiveStore();
          }
          if (Number(to.params.typeId) !== liveStore.liveTypeId) {
            liveStore.setTypeId(Number(to.params.typeId));
          }
          next();
        }
      },
    },
  ],
});

export default router;
