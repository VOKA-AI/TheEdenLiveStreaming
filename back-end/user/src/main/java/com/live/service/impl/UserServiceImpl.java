package com.live.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.entry.LiveAccount;
import com.live.entry.User;
import com.live.entry.UserRelationship;
import com.live.mapper.UserMapper;
import com.live.service.AmazonServiceImpl;
import com.live.service.LiveRoomService;
import com.live.service.UserService;
import com.live.vo.UserDetailedInfo;
import com.live.vo.UserInfoSaveVo;
import com.live.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AmazonServiceImpl amazonService;

    @Autowired
    private RelationshipServiceImpl relationshipService;

    @Autowired
    private LiveRoomServiceImpl liveRoomService;


    @Override
    public String uploadPortrait(MultipartFile file, String userName) throws IOException {

        //上传图片

        String url = amazonService.uploadFile(file);

        //对原来头像进行删除

        System.out.println("上传后照片路径为： " + url);
        //将图片路径进行保存
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",userName);

        User user = this.getBaseMapper().selectOne(queryWrapper);

        user.setPortrait(url);

        this.getBaseMapper().updateById(user);
        return url;
    }

    @Override
    public UserInfoVo getUserInfo(String username) {

        QueryWrapper<User> queryWrapper =  new QueryWrapper<User>();
        queryWrapper.eq("name",username);
        User user = this.getBaseMapper().selectOne(queryWrapper);

        UserInfoVo userInfoVo = new UserInfoVo(user.getId(),user.getName(),user.getNickname(),user.getPortrait(),user.getSelfIntroduction());

        return userInfoVo;
    }

    @Override
    public boolean saveUserInfo(UserInfoSaveVo userInfoSaveVo, String username) {
        QueryWrapper<User> queryWrapper =  new QueryWrapper<User>();
        queryWrapper.eq("name",username);
        User user = this.getBaseMapper().selectOne(queryWrapper);

        user.setSelfIntroduction(userInfoSaveVo.getSelfIntroduction());
        user.setNickname(userInfoSaveVo.getNickname());

        int i = this.getBaseMapper().updateById(user);

        if(i > 0)  return true;

        return false;
    }

    @Override
    public UserInfoVo getUserInfoById(Long userId) {
        QueryWrapper<User> queryWrapper =  new QueryWrapper<User>();
        queryWrapper.eq("id",userId);
        User user = this.getBaseMapper().selectOne(queryWrapper);

        if(user == null){
            //没有此用户
            return null;
        }
        UserInfoVo userInfoVo = new UserInfoVo(user.getId(),user.getName(),user.getNickname(),user.getPortrait(),user.getSelfIntroduction());

        return userInfoVo;
    }

    @Override
    public UserDetailedInfo getUserDetailedInfo(Long userId, String username) {

        if(userId == null && username != null){
            //根据用户名查询用户信息
            QueryWrapper<User> queryWrapper =  new QueryWrapper<User>();
            queryWrapper.eq("name",username);
            System.out.println("--------------------寻找用户----------------------");
            User user = this.getBaseMapper().selectOne(queryWrapper);
            if(user == null){
                //没有此用户
                return null;
            }

            System.out.println("--------------------用户找到了----------------------");
            UserDetailedInfo userDetailedInfo = new UserDetailedInfo();
            userDetailedInfo.setId(user.getId());
            userDetailedInfo.setName(user.getName());
            userDetailedInfo.setNickname(user.getNickname());
            userDetailedInfo.setPortrait(user.getPortrait());

            //获取关注人数
            System.out.println("--------------------寻找关注人数----------------------");
            QueryWrapper<UserRelationship> wrapper = new QueryWrapper<>();
            queryWrapper.eq("following_user_id",user.getId());//关注我的人
            queryWrapper.eq("is_deleted",0);
            Long count = relationshipService.getBaseMapper().selectCount(wrapper);

            userDetailedInfo.setFollowedNumber(count);

            //获取用户直播状态
            System.out.println("--------------------寻找直播间----------------------");
            QueryWrapper<LiveAccount> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id",user.getId());
            LiveAccount liveAccount = liveRoomService.getBaseMapper().selectOne(wrapper1);
            userDetailedInfo.setState(liveAccount.getState() == 1);

            return userDetailedInfo;

        }

        if(userId != null){
            //根据用户id查询用户信息
            QueryWrapper<User> queryWrapper =  new QueryWrapper<User>();
            queryWrapper.eq("id",userId);
            System.out.println("--------------------寻找用户----------------------");
            User user = this.getBaseMapper().selectOne(queryWrapper);
            if(user == null){
                //没有此用户
                return null;
            }
            System.out.println("--------------------用户找到了----------------------");
            UserDetailedInfo userDetailedInfo = new UserDetailedInfo();
            userDetailedInfo.setId(user.getId());
            userDetailedInfo.setName(user.getName());
            userDetailedInfo.setNickname(user.getNickname());
            userDetailedInfo.setPortrait(user.getPortrait());

            //获取关注人数
            System.out.println("--------------------寻找关注人数----------------------");
            QueryWrapper<UserRelationship> wrapper = new QueryWrapper<>();
            queryWrapper.eq("following_user_id",user.getId());//关注我的人
            queryWrapper.eq("is_deleted",0);
            Long count = relationshipService.getBaseMapper().selectCount(wrapper);

            userDetailedInfo.setFollowedNumber(count);

            //获取用户直播状态
            System.out.println("--------------------寻找直播间----------------------");
            QueryWrapper<LiveAccount> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("user_id",user.getId());
            LiveAccount liveAccount = liveRoomService.getBaseMapper().selectOne(wrapper1);
            userDetailedInfo.setState(liveAccount.getState() == 1);

            return userDetailedInfo;

        }

        return null;
    }

    @Override
    public Long getUserIdByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",username);
        User user = baseMapper.selectOne(queryWrapper);
        if(user == null){
            return -1L;
        }
        return user.getId();
    }

    @Override
    public String getUserNameById(Long userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        User user = baseMapper.selectById(userId);
        if(user == null){
            return null;
        }

        return user.getName();
    }
}
