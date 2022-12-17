package com.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.live.entry.LiveAccount;
import com.live.vo.LiveSquareVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


public interface LiveAccountMapper  extends BaseMapper<LiveAccount> {
    List<LiveSquareVo> getAllRooms();

    List<LiveSquareVo> getRoomsByTypeId(Long typeId);

}
