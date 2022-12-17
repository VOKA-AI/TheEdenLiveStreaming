package com.live.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.UserRelationship;
import com.live.vo.LiveFollowedVo;
import org.springframework.stereotype.Component;
import com.live.webapi.ResultObject;

@Component
public interface RelationshipService extends IService<UserRelationship> {
    ResultObject focusOn(Long userId,Long followedUserId);


    ResultObject CancelFollow(Long userId,Long followedUserId);

    /**
     * 判断关注状态
     * @param userId
     * @param followedUserId
     * @return
     */
    ResultObject judgeTheAttentionStatus(Long userId,Long followedUserId);

    ResultObject getFollowedRooms(Long userId, Page<LiveFollowedVo> page);

    ResultObject getFollowedCounts(Long userId);

}
