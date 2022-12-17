package com.live.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.component.JwtAuthenticationTokenFilter;
import com.live.entry.LiveAccount;
import com.live.entry.LiveInfo;
import com.live.entry.LiveTag;
import com.live.entry.User;
import com.live.mapper.LiveAccountMapper;
import com.live.service.LiveInfoService;
import com.live.service.LiveRoomService;
import com.live.service.LiveTagService;
import com.live.services.impls.IUsersServiceImpl;
import com.live.utils.JwtTokenUtil;
import com.live.vo.LiveInfoVo;
import com.live.vo.LiveSquareVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LiveRoomServiceImpl extends ServiceImpl<LiveAccountMapper, LiveAccount> implements LiveRoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    IUsersServiceImpl usersService;
    @Autowired
    LiveInfoServiceImpl liveInfoService;
    @Autowired
    LiveTagServiceImpl liveTagService;

    @Override
    public Boolean creteLiveRoom(User user) {
        //判断数据库是否含有该用户的直播间
        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        long count = this.baseMapper.selectCount(queryWrapper);
        //如果有就直接返回
        if(count > 0){
            return false;
        }
        //如果没有就创建一个
        String str = user.getId() + user.getName() + user.getPwd();
        LiveAccount liveAccount = new LiveAccount();
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String livePath = md5.digestHex(str);
        liveAccount.setLivePath(livePath);
        liveAccount.setState(0);
        liveAccount.setUserId(user.getId());

        return save(liveAccount);
    }

    @Override
    public String getLiveRoomPrivateKey(Long userId) {

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);
        if(liveAccount == null){
            return null;
        }
        return liveAccount.getLivePath();
    }

    @Override
    public Boolean judgePublish(String token, String livePath) {
        String username = jwtTokenUtil.getUserNameFromToken(token);
        LOGGER.info("checking username:{}", username);
        if(username == null) return false;
        User adminByUsername = usersService.getAdminByUsername(username);


        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",adminByUsername.getId());
        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);

        //开播状态改变
        if(liveAccount.getLivePath().equals(livePath)){
            //开播状态改变
            //进行数据库对应状态的修改
            liveAccount.setState(1);
            baseMapper.updateById(liveAccount);

            return true;
        }
        return false;
    }

    @Override
    public Boolean judgeUnPublish(String livePath) {

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("live_path",livePath);
        queryWrapper.eq("state",1);
        //将开播状态改为0
        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);
        if(liveAccount == null) return false;


        liveAccount.setState(0);
        if(baseMapper.updateById(liveAccount) > 0){
            LOGGER.info("修改直播状态成功");
            return true;
        }

        LOGGER.info("修改直播状态失败");
        return false;
    }

    @Override
    public List<LiveSquareVo> getAllLiveRoom() {
        //从数据库中找state为1的直播间
//        QueryWrapper<LiveAccount>  queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("state",1);
//        List<LiveAccount> list = baseMapper.selectList(queryWrapper);
        List<LiveSquareVo> allRooms = baseMapper.getAllRooms();
        //找对应的user
        for (int i = 0; i < allRooms.size(); i++) {
            //找对应的tags
            long infoId = allRooms.get(i).getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            allRooms.get(i).setTag(liveTags);
        }

        return allRooms;
    }

    @Override
    public List<LiveSquareVo> getLiveRoomsByType(long typeId) {
        List<LiveSquareVo> roomsByTypeId = baseMapper.getRoomsByTypeId(typeId);
        //找对应的user
        for (int i = 0; i < roomsByTypeId.size(); i++) {
            //找对应的tags
            long infoId = roomsByTypeId.get(i).getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            roomsByTypeId.get(i).setTag(liveTags);
        }
        return roomsByTypeId;
    }


    @Override
    public boolean save(LiveAccount entity) {
        return super.save(entity);
    }
}
