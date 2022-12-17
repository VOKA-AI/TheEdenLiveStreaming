package com.live.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.live.consant.BroadcastStatus;
import com.live.entry.LiveAccount;
import com.live.entry.LiveType;
import com.live.entry.User;
import com.live.models.AdminUserDetails;
import com.live.service.impl.*;

import com.live.vo.LiveInfoVo;
import com.live.vo.LiveRecordingVo;
import com.live.vo.LiveSquareVo;
import com.live.vo.LiveTypeVo;
import com.live.consant.LiveConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.live.webapi.ResultCode;
import com.live.webapi.ResultObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/live")
@Api("直播")
public class LiveController {

    @Autowired
    private LiveRoomServiceImpl liveRoomService;
    @Autowired
    private LiveInfoServiceImpl liveInfoService;
    @Autowired
    private LiveTypeServiceImpl liveTypeService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private LiveRecordingServiceImpl recordingService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${recording.minTime}")
    private Integer minRecordingTime;

    @RequestMapping("/create_live_room")
    @ApiOperation("创建直播间")
    public ResultObject createLiveRoom(){

        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        User user = adminUserDetails.getUser();
        if(user == null){
            return ResultObject.failed("用户信息异常");
        }
        //创建直播间
        liveRoomService.creteLiveRoom(user);

        return ResultObject.success("获取直播间成功");
    }

    @GetMapping("/get_private_key")
    @ApiOperation("获取密钥")
    public ResultObject getPrivateKey(HttpServletRequest request){
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        User user = adminUserDetails.getUser();

        //从数据库中找对应user的直播间密钥
        String liveRoomPrivateKey = liveRoomService.getLiveRoomPrivateKey(user.getId());
        if(liveRoomPrivateKey == null){
            return ResultObject.failed("获取有误，清重新获取直播间");
        }
        //和token拼接后返回
        String resStr = liveRoomPrivateKey + "?" + request.getHeader(tokenHeader);

        JSONObject object = new JSONObject();
        object.put("privateKey",resStr);

        return ResultObject.success(object);
    }

    @RequestMapping("/edit_live_info")
    @ApiOperation("编辑直播间信息")
    public ResultObject editLiveRoomInfo(@RequestBody LiveInfoVo liveInfoVo){
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        User user = adminUserDetails.getUser();
        if(user == null){
            return ResultObject.failed("用户信息异常,请先登录");
        }

        liveInfoService.saveLiveRoomInfo(liveInfoVo, user.getId());

        return ResultObject.success("保存信息成功");
    }


    @GetMapping("/get_all_online_rooms")
    @ApiOperation("获取当前所有直播信息")
    public ResultObject getAllOnlineRooms(Integer num,Integer size){
        if( num == null || size == null){
            return ResultObject.failed("传入参数有误");
        }

        Page<LiveSquareVo> page = new Page<>(num, size);

        return liveRoomService.getAllLiveRoom(page);
    }

    @GetMapping("/get_all_types")
    @ApiOperation("获取直播类型")
    public ResultObject getTypesList(){
        QueryWrapper<LiveType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        List<LiveType> liveTypes = liveTypeService.getBaseMapper().selectList(queryWrapper);
        List<LiveTypeVo> resList = new ArrayList<>();
        for (int i = 0; i < liveTypes.size(); i++) {
            LiveTypeVo liveTypeVo = new LiveTypeVo();
            liveTypeVo.setType(liveTypes.get(i).getType());
            liveTypeVo.setTypeId(liveTypes.get(i).getId());
            liveTypeVo.setIconUrl(liveTypes.get(i).getIconUrl());
            resList.add(liveTypeVo);
        }
        return ResultObject.success(resList,"获取所有类型");
    }

    @RequestMapping("/get_room_by_type")
    @ApiOperation("通过类型获取直播间")
    public  ResultObject getRoomsByType(@Param("typeId") Long typeId,Integer num,Integer size){
        if(num == null || size == null){
            return ResultObject.failed("参数传入错误");
        }
        Page<LiveSquareVo> page = new Page<>(num, size);

        return liveRoomService.getLiveRoomsByType(typeId,page);
    }


    @GetMapping("/get_rooms_on_top")
    @ApiOperation("获取热度前4的直播间")
    public ResultObject getRoomsOnTop(){

        List<LiveSquareVo> roomsOnTop = liveRoomService.getRoomsOnTop();


        return ResultObject.success(roomsOnTop,"获取直播信息成功");
    }

    @PostMapping("/get_room_by_username")
    public ResultObject getRoomByUsername(@Param("username") String username){

        Long userId = userService.getUserIdByName(username);
        if(userId == -1) {
            return ResultObject.failed("找不到对应用户");
        }
        LiveSquareVo liveRoom = liveRoomService.getLiveRoomsByUserID(userId);
        if(liveRoom == null){
            return ResultObject.failed("没有编辑直播间信息，请先进行编辑");
        }


        return ResultObject.success(liveRoom);
    }

    @GetMapping("/get_edit_info")
    public ResultObject getEditInfo(Principal principal){
        Long userId = userService.getUserIdByName(principal.getName());
        return liveInfoService.getEditInfo(userId);
    }

    @PostMapping("/start_publish")
    @ApiOperation("一键开播")
    public ResultObject startPublish(Principal principal){


        //  存在
        //      判断推流是否成功
        //          成功：将直播间状态设置为status = 1
        //          失败：将错误信息返回给前端
        String username = principal.getName();
        Long userId = userService.getUserIdByName(username);

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        LiveAccount liveAccount = liveRoomService.getBaseMapper().selectOne(queryWrapper);
        if(liveAccount.getState() == 1){
            return ResultObject.success("已经开播过！");
        }

        //1. 判断Map里面当前用户的key是否存在
        if(!LiveConstant.statusMap.containsKey(username)){
            //  不存在
            //      说明开播时密钥有误
            return ResultObject.failed("开播失败，请重新推流和检查密钥是否正确！");
        }
        BroadcastStatus broadcastStatus = LiveConstant.statusMap.get(username);

        //上次直播是5分钟内下线的
        if(broadcastStatus.getResultCode().getCode() == 503){
            return ResultObject.failed("请重新推流");
        }

        if(broadcastStatus.getResultCode().getCode() == 502){
            return new ResultObject(broadcastStatus.getResultCode().getCode(),broadcastStatus.getResultCode().getMessage(),null);
        }

        BroadcastStatus status = new BroadcastStatus();
        status.setResultCode(ResultCode.START_PUBLISH);
        status.setTime(broadcastStatus.getTime());

        LiveConstant.statusMap.put(username,status);



        UpdateWrapper<LiveAccount> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id",userId).set("state",1);
        liveRoomService.update(null,updateWrapper);
        return ResultObject.success("开播成功");
    }


    @GetMapping("/get_recording")
    @ApiOperation("获取直播历史")
    public ResultObject getRecording(Integer num,Integer size,Long userId){
        if( num == null || size == null || userId == null){
            return ResultObject.failed("传入参数有误");
        }
        Page<LiveRecordingVo> page = new Page<>(num, size);
        return recordingService.getRecordingByUserId(userId,page);
    }



    @PostMapping("/upload_cover")
    @ApiOperation("上传直播封面")
    public ResultObject uploadCover(@RequestPart(value = "file") MultipartFile file,Principal principal){
        if(file == null || file.isEmpty()){
            return ResultObject.failed("请选择封面上传");
        }

        String username =  principal.getName();
        Long userId = userService.getUserIdByName(username);
        String url = "";
        try{
            url = liveRoomService.uploadCover(file,userId);
        } catch (IOException e){
            return ResultObject.failed("发生未知错误，上传封面失败");
        }
        JSONObject object = new JSONObject();
        object.put("coverUrl",url);

        return ResultObject.success(object,"上传封面成功");
    }


    @GetMapping("/get_room_state")
    @ApiOperation("获取直播间直播状态")
    public ResultObject getRoomState(Long roomId){
        return liveRoomService.getLiveRoomsStatus(roomId);
    }


}
