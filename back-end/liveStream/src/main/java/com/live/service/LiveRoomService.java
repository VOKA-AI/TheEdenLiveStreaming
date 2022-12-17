package com.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.LiveAccount;
import com.live.entry.User;
import com.live.vo.LiveInfoVo;
import com.live.vo.LiveSquareVo;

import java.util.List;

public interface LiveRoomService extends IService<LiveAccount> {
    Boolean creteLiveRoom(User user);

    String getLiveRoomPrivateKey(Long userId);

    Boolean judgePublish(String token,String livePath);

    Boolean judgeUnPublish(String livePath);

    List<LiveSquareVo> getAllLiveRoom();

    List<LiveSquareVo> getLiveRoomsByType(long typeId);

}
