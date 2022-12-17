package com.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.entry.LiveAccount;
import com.live.entry.LiveInfo;
import com.live.entry.LiveTag;
import com.live.entry.LiveType;
import com.live.mapper.LiveInfoMapper;
import com.live.service.LiveInfoService;
import com.live.vo.LiveInfoShowVo;
import com.live.vo.LiveInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.live.webapi.ResultObject;

import java.util.List;

@Service
public class LiveInfoServiceImpl extends ServiceImpl<LiveInfoMapper, LiveInfo> implements LiveInfoService {

    @Autowired
    private LiveTagServiceImpl liveTagService;
    @Autowired
    private LiveTypeServiceImpl liveTypeService;

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
        if(liveInfoVo.getTypeId() != null){
            liveInfo.setTypeId(liveInfoVo.getTypeId());
        }

        save(liveInfo);
        //再绑定tag
        long infoId = liveInfo.getId();
        List<String> list = liveInfoVo.getTags();

        if(list != null){
            for (int i = 0; i < list.size(); i++) {
                LiveTag liveTag = new LiveTag();
                liveTag.setTag(list.get(i));
                liveTag.setInfoId(infoId);
                liveTagService.getBaseMapper().insert(liveTag);
            }
        }

        return true;
    }

    @Override
    public ResultObject getEditInfo(Long userID) {
        QueryWrapper<LiveInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userID);
        wrapper.eq("is_deleted",0);
        LiveInfo liveInfo = baseMapper.selectOne(wrapper);

        if(liveInfo == null){
            return ResultObject.failed("没有编辑过的信息");
        }

        QueryWrapper<LiveTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("info_id",liveInfo.getId());
        List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(queryWrapper);

        LiveType liveType = liveTypeService.getBaseMapper().selectById(liveInfo.getTypeId());

        QueryWrapper<LiveAccount> liveAccountQueryWrapper = new QueryWrapper<>();
        liveAccountQueryWrapper.eq("user_id",userID);


        LiveInfoShowVo liveInfoShowVo = new LiveInfoShowVo();
        liveInfoShowVo.setIntroduction(liveInfo.getIntroduction());
        liveInfoShowVo.setTitle(liveInfo.getTitle());
        liveInfoShowVo.setTag(liveTags);
        if(liveType != null){
            liveInfoShowVo.setType(liveType.getType());
            liveInfoShowVo.setTypeId(liveType.getId());
        }

        return ResultObject.success(liveInfoShowVo,"获取成功");
    }


}
