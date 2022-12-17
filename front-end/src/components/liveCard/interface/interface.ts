type tag = {
  id: number;
  tag: string;
};

export type LiveCard = {
  userId: number;
  userPortraitUrl: null | string;
  userName: string;
  roomId: number;
  livePath: string;
  onlineNumber?: number;
  infoId: number;
  title: string;
  introduction: string;
  tags: tag[];
  coverUrl: string;
  startTime?: string;
  endTime?: string;
};

export type PageConfig = {
  pageSize: number;
  total: number;
  currentPage: number;
};
