package com.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.live.entry.LiveInfo;
import com.live.entry.LiveRecording;
import com.live.vo.LiveRecordingVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LiveRecordingMapper extends BaseMapper<LiveRecording> {
    IPage<LiveRecordingVo> getRecordingByUserId(Long userId,IPage<LiveRecordingVo> page);
}
