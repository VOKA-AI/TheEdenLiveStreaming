package com.live.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.LiveAccount;
import com.live.entry.User;
import com.live.vo.LiveSquareVo;
import org.springframework.web.multipart.MultipartFile;
import com.live.webapi.ResultObject;

import java.io.IOException;
import java.util.List;

public interface LiveRoomService extends IService<LiveAccount> {
    /**
     * 创建直播间
     * @param user
     * @return
     */
    Boolean creteLiveRoom(User user);

    /**
     * 获取直播间密钥
     * @param userId
     * @return
     */
    String getLiveRoomPrivateKey(Long userId);

    /**
     * 判断是否开播,修改状态
     * @param token
     * @param livePath
     * @return
     */
    Boolean judgePublish(String token,String livePath);

    /**
     * 判断是否停播，修改状态
     * @param livePath
     * @return
     */
    Boolean judgeUnPublish(String livePath);

    /**
     * 获取所有直播间
     * @return
     */
    ResultObject getAllLiveRoom(Page<LiveSquareVo> page);

    /**
     * 获取热度前4的直播间
     * @return
     */
    List<LiveSquareVo> getRoomsOnTop();

    /**
     * 根据类型获取直播间
     * @param typeId
     * @return
     */
    ResultObject getLiveRoomsByType(long typeId,Page<LiveSquareVo> page);

    ResultObject getLiveRoomsStatus(Long roomId);

    LiveSquareVo getLiveRoomsByUserID(long userId);

    String uploadCover(MultipartFile file,Long userId) throws IOException;


    /**
     * 观众收看直播回调
     * @param livePath
     * @return
     */
    Boolean watchCallback(String livePath);

    /**
     * 停止收看回调
     * @param livePath
     * @return
     */
    Boolean stopWatchCallback(String livePath);

}
