package com.live.web;

import com.live.entry.User;
import com.live.service.impl.LiveInfoServiceImpl;
import com.live.service.impl.LiveRoomServiceImpl;
import com.live.service.impl.UserServiceImpl;
import com.live.services.impls.IUsersServiceImpl;
import com.live.vo.LiveInfoVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest(classes = WebApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebApplicationTests {

    @Resource
    private IUsersServiceImpl usersService;
    @Autowired
    private LiveRoomServiceImpl liveRoomService;
    @Autowired
    private LiveInfoServiceImpl liveInfoService;
    @Test
    void contextLoads() {

    }

    @Test
    void createUser(){
        //创建测试数据
        String[] usernames = {"小酒窝","赴情深","薄情辞","慢半拍","踏歌行","初相识","饮晚风","江寰词" ,"耿风词","顾清辞"};
        String[] titles = {"英雄联盟" ,
                "DOTA2" ,
                "CS:GO" ,
                "APEX英雄" ,
                "永劫无间" ,
                "穿越火线" ,
                "守望先锋" ,
                "吃鸡行动" ,
                "逃离塔科夫" ,
                "传奇" };
        String[] introduction = {"1","2","3","4","5","6","7","8","9","10"};


        for (int i = 0; i < usernames.length; i++) {
            User user = new User();
            user.setName(usernames[i]);
            user.setPwd("123456");
            user.setMail("1489782000@qq.com");
            //注册
            usersService.register(user);
            //创建直播间
            user = usersService.getAdminByUsername(user.getName());
            liveRoomService.creteLiveRoom(user);
            //创建编辑直播间信息
            LiveInfoVo liveInfoVo = new LiveInfoVo();
            liveInfoVo.setTitle(titles[i]);
            liveInfoVo.setIntroduction(introduction[i]);
            liveInfoVo.setTypeId(2);

            liveInfoService.saveLiveRoomInfo(liveInfoVo,user.getId());
        }


    }


















}
