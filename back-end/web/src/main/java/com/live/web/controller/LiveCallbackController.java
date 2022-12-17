package com.live.web.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.live.entry.User;
import com.live.models.AdminUserDetails;
import com.live.service.impl.LiveRoomServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapi.ResultObject;

@RestController
@RequestMapping("/live_callback")
public class LiveCallbackController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private LiveRoomServiceImpl liveRoomService;

    @RequestMapping("/publish_callback")
    public Integer publishCallBack(@RequestBody Object o){
//        System.out.println(o);

        JSONObject jsonObject = JSONUtil.parseObj(o);
//        System.out.println(jsonObject);
//        System.out.println("param" + jsonObject.get("param"));
//        System.out.println(jsonObject.get("tcUrl"));
        //获取对应的param,判断token是否合法,还有对应的直播间路径是否存在
        String param = (String) jsonObject.get("param");
        String token = param.substring(tokenHead.length());
        String livePath = (String) jsonObject.get("stream");
        if (liveRoomService.judgePublish(token,livePath)) {
            return 0;
        }
        return 500;
    }



    @RequestMapping("/stop_callback")
    public Integer stopCallBack(@RequestBody Object o){

        JSONObject jsonObject = JSONUtil.parseObj(o);
        String livePath = (String) jsonObject.get("stream");
        System.out.println(o);

        liveRoomService.judgeUnPublish(livePath);
        return 0;
    }

    @RequestMapping("/watch_callback")
    public Integer watchCallBack(@RequestBody Object o){

        System.out.println(o);
        return 0;
    }



    @RequestMapping("/stop_watch_callback")
    public Integer stopWatchCallBack(@RequestBody Object o){

        System.out.println(o);
        return 0;
    }


}
