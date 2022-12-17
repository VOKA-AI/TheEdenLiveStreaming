package com.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.live.entry.LiveAccount;
import com.live.vo.LiveSquareVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface LiveAccountMapper  extends BaseMapper<LiveAccount> {
    IPage<LiveSquareVo> getAllRooms(IPage<LiveSquareVo> page);

    IPage<LiveSquareVo> getRoomsByTypeId(Long typeId,IPage<LiveSquareVo> page);

    List<LiveSquareVo> getRoomsOnTop();

    LiveSquareVo getRoomByUserID(long userId);
}
