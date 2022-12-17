package com.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.entry.LiveAccount;
import com.live.entry.LiveInfo;
import com.live.entry.LiveTag;
import com.live.mapper.LiveAccountMapper;
import com.live.mapper.LiveInfoMapper;
import com.live.service.LiveInfoService;
import com.live.vo.LiveInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiveInfoServiceImpl extends ServiceImpl<LiveInfoMapper, LiveInfo> implements LiveInfoService {

    @Autowired
    private LiveTagServiceImpl liveTagService;

    @Override
    public Boolean saveLiveRoomInfo(LiveInfoVo liveInfoVo, Long user_id) {

        //判断是否已经有该用户的直播间信息了
        //      如果有 得将信息deleted置为1
        QueryWrapper<LiveInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user_id);
        wrapper.eq("is_deleted",0);
        LiveInfo liveInfo1 = baseMapper.selectOne(wrapper);

        if(liveInfo1 != null ){
            //将deleted设置为1
            liveInfo1.setIsDeleted(1);
            baseMapper.update(liveInfo1,wrapper);
        }

        //先创建直播间信息
        LiveInfo liveInfo = new LiveInfo();
        liveInfo.setTitle(liveInfoVo.getTitle());
        liveInfo.setIntroduction(liveInfoVo.getIntroduction());
        liveInfo.setUserId(user_id);
        //指定对应type
        liveInfo.setTypeId(liveInfoVo.getTypeId());
        save(liveInfo);
        //再绑定tag
        long infoId = liveInfo.getId();
        List<String> list = liveInfoVo.getTag();

        for (int i = 0; i < list.size(); i++) {
            LiveTag liveTag = new LiveTag();
            liveTag.setTag(list.get(i));
            liveTag.setInfoId(infoId);
            liveTagService.getBaseMapper().insert(liveTag);
        }

        return true;
    }
}
