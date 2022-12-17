package com.live.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.entry.LiveTag;
import com.live.entry.UserRelationship;
import com.live.mapper.RelationshipMapper;
import com.live.service.RelationshipService;
import com.live.vo.LiveFollowedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.live.webapi.ResultObject;

import java.util.List;

@Service
public class RelationshipServiceImpl extends ServiceImpl<RelationshipMapper, UserRelationship> implements RelationshipService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LiveTagServiceImpl liveTagService;

    @Override
    public ResultObject focusOn(Long userId, Long followedUserId) {
        QueryWrapper<UserRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("following_user_id",followedUserId);
        UserRelationship userRelationship = baseMapper.selectOne(queryWrapper);

        //判断用户是否存在
        if(userService.getUserNameById(followedUserId) == null){
            return ResultObject.failed("关注的用户不存在");
        }

        if(userRelationship != null && userRelationship.getIsDeleted() == 0){
            return ResultObject.failed("已经关注过了");
        }

        if(userRelationship != null){
            //记录存在，但已经取消关注
            //把deleted改为0就行
            userRelationship.setIsDeleted(0);
            baseMapper.update(userRelationship,queryWrapper);
            return ResultObject.success("关注成功");
        }

        UserRelationship newUserRelationship = new UserRelationship();
        newUserRelationship.setUserId(userId);
        newUserRelationship.setFollowingUserId(followedUserId);
        int insert = baseMapper.insert(newUserRelationship);

        if(insert > 0){
            return ResultObject.success("关注成功");
        } else {
            return ResultObject.failed("关注失败");
        }
    }

    @Override
    public ResultObject CancelFollow(Long userId, Long followedUserId) {
        //判断用户是否存在
        if(userService.getUserNameById(followedUserId) == null){
            return ResultObject.failed("关注的用户不存在");
        }
        QueryWrapper<UserRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("following_user_id",followedUserId);
        UserRelationship userRelationship = baseMapper.selectOne(queryWrapper);

        if(userRelationship != null && userRelationship.getIsDeleted() == 1){
            return ResultObject.failed("已经取消关注过了");
        }
        if(userRelationship == null){
            return ResultObject.failed("没有关注过该用户");
        }
        userRelationship.setIsDeleted(1);
        baseMapper.update(userRelationship,queryWrapper);

        return ResultObject.success("取消关注成功");
    }

    @Override
    public ResultObject judgeTheAttentionStatus(Long userId, Long followedUserId) {
        QueryWrapper<UserRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId).eq("following_user_id",followedUserId);
        UserRelationship userRelationship = baseMapper.selectOne(queryWrapper);
        JSONObject jsonObject = new JSONObject();
        if(userRelationship != null && userRelationship.getIsDeleted() == 1){

            jsonObject.put("isFollowed", false);//没有被关注
            return ResultObject.success(jsonObject,"未关注");
        }

        if(userRelationship != null && userRelationship.getIsDeleted() == 0){
            jsonObject.put("isFollowed",true);//已经被关注
            return ResultObject.success(jsonObject,"已关注");
        }

        jsonObject.put("isFollowed",false);//没有被关注
        return ResultObject.success(jsonObject,"未关注");
    }

    @Override
    public ResultObject getFollowedRooms(Long userId, Page<LiveFollowedVo> page) {
//        page.setOptimizeCountSql(false);
        IPage<LiveFollowedVo> relationshipRooms = baseMapper.getRelationshipRooms(userId, page);
        for (LiveFollowedVo record : relationshipRooms.getRecords()) {
            long infoId = record.getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            record.setTag(liveTags);
        }

        return ResultObject.success(relationshipRooms,"获取关注的直播间成功");
    }

    @Override
    public ResultObject getFollowedCounts(Long userId) {
        if(userId == null){
            return ResultObject.failed("参数错误");
        }

        QueryWrapper<UserRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("following_user_id",userId);//关注我的人
        queryWrapper.eq("is_deleted",0);
        Long count = baseMapper.selectCount(queryWrapper);

        return ResultObject.success(count,"关注我的人数");
    }


}
