import serve from "@/utils/axios";

export const reqGetCarousel = () => {
  return serve({
    url: "/live/get_rooms_on_top",
    method: "get",
  });
};

export const reqGetOnlinRooms = (num: number = 0, size: number = 20) => {
  return serve({
    method: "get",
    url: "/live/get_all_online_rooms",
    params: {
      num,
      size,
    },
  });
};

export const reqGetAllOnlinRoomsFollowed = (
  num: number = 0,
  size: number = 20
) => {
  return serve({
    method: "get",
    url: "/friendship/get_followed_rooms",
    params: {
      num,
      size,
    },
  });
};
