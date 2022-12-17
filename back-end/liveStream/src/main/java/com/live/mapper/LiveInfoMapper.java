package com.live.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.live.entry.LiveAccount;
import com.live.entry.LiveInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LiveInfoMapper extends BaseMapper<LiveInfo> {
}
