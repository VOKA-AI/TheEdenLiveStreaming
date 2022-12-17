import serve from "@/utils/axios";

export const reqGetRoomByType = ({
  typeId,
  num = 1,
  size = 20,
}: {
  typeId: number;
  num: number;
  size: number;
}) => {
  return serve({
    url: "/live/get_room_by_type",
    method: "post",
    headers: {
      "Content-Type": "application/json",
    },
    data: {
      typeId,
      num,
      size,
    },
    params: {
      typeId,
      num,
      size,
    },
  });
};
