package com.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.entry.LiveRecording;
import com.live.entry.LiveTag;
import com.live.mapper.LiveRecordingMapper;
import com.live.service.LiveRecordingService;
import com.live.vo.LiveRecordingVo;
import com.live.vo.LiveSquareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.live.webapi.ResultObject;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LiveRecordingServiceImpl extends ServiceImpl<LiveRecordingMapper, LiveRecording> implements LiveRecordingService {

    @Autowired
    private LiveTagServiceImpl liveTagService;
    @Autowired
    private LiveRoomServiceImpl liveRoomService;

    @Override
    public ResultObject createRecording(Timestamp startTime, Timestamp endTime, Long info_id,Long user_id) {
        LiveRecording liveRecording = new LiveRecording();
        liveRecording.setLiveStartTime(startTime);
        liveRecording.setLiveEndTime(endTime);
        liveRecording.setInfoId(info_id);
        //读取存入当前封面
        LiveSquareVo liveRooms = liveRoomService.getLiveRoomsByUserID(user_id);
        liveRecording.setCoverUrl(liveRooms.getCoverUrl());

        int insert = baseMapper.insert(liveRecording);
        if(insert > 0){
            return ResultObject.success("创建历史成功");
        }
        return ResultObject.failed("创建历史失败");
    }

    @Override
    public ResultObject getRecordingByUserId(Long userId, Page<LiveRecordingVo> page) {
        IPage<LiveRecordingVo> recordingByUserId = baseMapper.getRecordingByUserId(userId, page);

        for (LiveRecordingVo record : recordingByUserId.getRecords()) {
            long infoId = record.getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            record.setTags(liveTags);
        }

        return ResultObject.success(recordingByUserId,"获取历史信息成功");
    }


}
