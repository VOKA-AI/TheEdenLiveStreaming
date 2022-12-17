package com.live.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.LiveRecording;
import com.live.vo.LiveRecordingVo;
import com.live.webapi.ResultObject;

import java.sql.Timestamp;

public interface LiveRecordingService extends IService<LiveRecording> {
    ResultObject createRecording(Timestamp startTime, Timestamp endTime, Long info_id,Long user_id);

    ResultObject getRecordingByUserId(Long userId, Page<LiveRecordingVo> page);

}
