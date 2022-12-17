package com.live.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.entry.LiveTag;
import com.live.mapper.LiveTagMapper;
import com.live.service.LiveTagService;
import org.springframework.stereotype.Service;

@Service
public class LiveTagServiceImpl extends ServiceImpl<LiveTagMapper, LiveTag> implements LiveTagService {

}
