package com.live.web.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.live.service.impl.LiveRecordingServiceImpl;
import com.live.service.impl.LiveRoomServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/live_callback")
@Api("直播回调")
public class LiveCallbackController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private LiveRoomServiceImpl liveRoomService;
    @Autowired
    private LiveRecordingServiceImpl liveRecordingService;

    @RequestMapping("/publish_callback")
    @ApiOperation("开播回调")
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
        return 0;
    }



    @RequestMapping("/stop_callback")
    @ApiOperation("停播回调")
    public Integer stopCallBack(@RequestBody Object o){

        JSONObject jsonObject = JSONUtil.parseObj(o);
        String livePath = (String) jsonObject.get("stream");
        System.out.println(o);

        liveRoomService.judgeUnPublish(livePath);
        return 0;
    }

    @RequestMapping("/watch_callback")
    @ApiOperation("收看直播回调")
    public Integer watchCallBack(@RequestBody Object o){
        //直播观看人数加1
        //根据stream就可以获取对应直播间
        System.out.println("--------------收看直播回调-------------");
        JSONObject jsonObject = new JSONObject(o);
        String livePath = (String) jsonObject.get("stream");

        System.out.println(o);

        Boolean callback = liveRoomService.watchCallback(livePath);
        System.out.println("-------是否成功------" + callback);
        return callback ? 0 : -1;
    }



    @RequestMapping("/stop_watch_callback")
    @ApiOperation("停止收看直播回调")
    public Integer stopWatchCallBack(@RequestBody Object o){
        //直播观看人数减1
        System.out.println("--------------停止收看回调-------------");
        JSONObject jsonObject = new JSONObject(o);
        String livePath = (String) jsonObject.get("stream");

        System.out.println(o);

        Boolean callback = liveRoomService.stopWatchCallback(livePath);
        return 0;
    }


}
