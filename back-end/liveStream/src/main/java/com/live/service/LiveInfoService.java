package com.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.LiveInfo;
import com.live.vo.LiveInfoVo;
import com.live.webapi.ResultObject;

public interface LiveInfoService extends IService<LiveInfo> {
    Boolean saveLiveRoomInfo(LiveInfoVo liveInfoVo, Long user_id);


    ResultObject getEditInfo(Long userID);

}
