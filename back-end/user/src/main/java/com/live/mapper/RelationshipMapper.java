package com.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.live.entry.UserRelationship;
import com.live.vo.LiveFollowedVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RelationshipMapper extends BaseMapper<UserRelationship>{
    IPage<LiveFollowedVo> getRelationshipRooms(long userId, IPage<LiveFollowedVo> page);
}
