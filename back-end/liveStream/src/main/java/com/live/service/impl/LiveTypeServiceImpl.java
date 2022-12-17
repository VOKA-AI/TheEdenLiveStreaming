package com.live.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.entry.LiveType;
import com.live.mapper.LiveTypeMapper;
import com.live.service.LiveTypeService;
import org.springframework.stereotype.Service;

@Service
public class LiveTypeServiceImpl extends ServiceImpl<LiveTypeMapper, LiveType> implements LiveTypeService {

}
