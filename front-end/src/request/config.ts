import serve from "@/utils/axios";
export interface roomInfo {
  title: string;
  introduction: string;
  typeId: number;
  tags: string[];
}
export const getKeyword = () => {
  return serve({
    url: "/live/get_private_key/",
    method: "get",
  });
};

export const postRoomInfo = (data: roomInfo) => {
  return serve({
    url: "/live/edit_live_info",
    method: "post",
    data: data,
    headers: {
      "conten-type": "application/json;charset=UTF-8 ",
    },
  });
};

export const getLiveRoom = (name: string) => {
  const params = new URLSearchParams();
  params.append("username", name);
  return serve({
    url: "/live/get_room_by_username",
    method: "post",
    data: params,
  });
};
export const getTypes = () => {
  return serve({
    url: "/live/get_all_types",
    method: "get",
  });
};

export const postStartOrder = () => {
  return serve({
    url: "/live/start_publish",
    method: "post",
  });
};

export const postCreateRoom = () => {
  return serve({
    url: "/live/create_live_room",
    method: "post",
  });
};

export const getRoomState = (roomId: number) => {
  return serve({
    url: "/live/get_room_state",
    method: "get",
    params: {
      roomId,
    },
  });
};
export const getConfigureInfo = () => {
  return serve({
    url: "/live/get_edit_info",
    method: "get",

  });
};
export const  postCoverImg = (data:FormData)=>{
  return serve({
    url:'/live/upload_cover',
    method:'post',
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    data:data
  })
}
