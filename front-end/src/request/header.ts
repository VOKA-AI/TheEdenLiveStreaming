import serve from "@/utils/axios";

export const reqGetAllType = () => {
  return serve({
    method: "get",
    url: "/live/get_all_types",
  });
};
