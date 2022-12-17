package com.live.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.live.service.impl.RelationshipServiceImpl;
import com.live.service.impl.UserServiceImpl;
import com.live.vo.LiveFollowedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.live.webapi.ResultObject;

import java.security.Principal;

@RestController
@RequestMapping("/friendship")
@Api(tags = "用户关注")
public class FriendShipController {

    @Autowired
    private RelationshipServiceImpl relationshipService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/focus_on")
    @ApiOperation("关注用户")
    public ResultObject FocusOn(Long followedUserId, Principal principal){
        Long userId = userService.getUserIdByName(principal.getName());
        if(followedUserId == null){
            return ResultObject.failed("被关注id不能为空");
        }
        if(followedUserId.equals(userId)){
            return ResultObject.failed("不能关注自己");
        }

        return relationshipService.focusOn(userId,followedUserId );
    }

    @PostMapping("/cancel_follow")
    @ApiOperation("取消关注")
    public ResultObject CancelFollow(Long followedUserId, Principal principal){
        //判断用户是否存在
        Long userId = userService.getUserIdByName(principal.getName());
        if(followedUserId == null){
            return ResultObject.failed("被关注id不能为空");
        }
        if(userService.getUserNameById(followedUserId) == null){
            return ResultObject.failed("关注的用户不存在");
        }
        return relationshipService.CancelFollow(userId,followedUserId );
    }

    @GetMapping("/judge_isFollowed")
    @ApiOperation("判断关注状态")
    public ResultObject JudgeIsFollowed(Long followedUserId, Principal principal){
        //判断用户是否存在
        Long userId = userService.getUserIdByName(principal.getName());
        if(followedUserId == null){
            return ResultObject.failed("被关注id不能为空");
        }
        if(userService.getUserNameById(followedUserId) == null){
            return ResultObject.failed("关注的用户不存在");
        }

        return relationshipService.judgeTheAttentionStatus(userId,followedUserId);
    }

    @GetMapping("/get_followed_rooms")
    @ApiOperation("获取关注列表")
    public ResultObject getFollowedRooms(Principal principal,Integer num,Integer size){
        if(num == null || size == null){
            return ResultObject.failed("传入参数有误");
        }

        String name = principal.getName();
        Long userId = userService.getUserIdByName(name);
        Page<LiveFollowedVo> page = new Page<>(num,size);

        return relationshipService.getFollowedRooms(userId,page);
    }

    @GetMapping("/get_followed_counts")
    @ApiOperation("获取关注人数")
    public  ResultObject getFollowedCounts(Long userId){
        return relationshipService.getFollowedCounts(userId);
    }
}
