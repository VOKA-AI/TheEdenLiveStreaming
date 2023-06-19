import serve from "@/utils/axios";

export const getFollowers = (userId: number) => {
  return serve({
    url: "/friendship/get_followed_counts",
    method: "get",
    params: {
      userId,
    },
  });
};

export const getRoomByType = (typeId: number, num: number, size: number) => {
  return serve({
    url: "/live/get_room_by_type",
    method: "post",
    params: {
      typeId,
      num,
      size,
    },
  });
};

interface userInfoParam {
  userName?: string;
  userId?: number;
}
export const getPlayerInfo = (obj: userInfoParam) => {
  let params = {};
  if (obj.userName && obj.userName !== "") {
    params = { username: obj.userName };
  } else if (obj.userId) {
    params = { userId: obj.userId };
  }
  //console.log("params", params);
  return serve({
    url: "/users/get_user_info_by_id_or_name",
    method: "get",
    params: params,
  });
};
