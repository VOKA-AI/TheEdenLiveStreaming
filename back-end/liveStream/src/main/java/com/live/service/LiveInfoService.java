package com.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.LiveAccount;
import com.live.entry.LiveInfo;
import com.live.vo.LiveInfoVo;

public interface LiveInfoService extends IService<LiveInfo> {
    Boolean saveLiveRoomInfo(LiveInfoVo liveInfoVo, Long user_id);
}
