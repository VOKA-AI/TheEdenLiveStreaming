import serve from "@/utils/axios";

export const getRecording = ({
  num,
  size,
  userId,
}: {
  num: number;
  size: number;
  userId: number;
}) => {
  return serve({
    url: "/live/get_recording",
    params: {
      num,
      size,
      userId,
    },
  });
};

export const getFollowState = (userId: number) => {
  return serve({
    url: "/friendship/judge_isFollowed",
    method: "get",
    params: { followedUserId: userId },
  });
};

export const setFollowState = (userId: number) => {
  const params = new URLSearchParams();
  params.append("followedUserId", userId as unknown as string);
  return serve({
    url: "/friendship/focus_on",
    method: "post",
    params: {
      followedUserId: userId,
    },
  });
};

export const setUnfollowState = (userId: number) => {
  const params = new URLSearchParams();
  params.append("followedUserId", userId as unknown as string);
  return serve({
    url: "/friendship/cancel_follow",
    method: "post",
    params: {
      followedUserId: userId,
    },
  });
};

export const getUserData = () => {
  return serve({
    method: "get",
    url: "/users/get_user_info",
  });
};
