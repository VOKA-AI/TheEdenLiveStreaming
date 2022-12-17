package com.live.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.live.entry.LiveInfo;
import com.live.entry.LiveType;
import com.live.entry.User;
import com.live.models.AdminUserDetails;
import com.live.service.LiveRoomService;
import com.live.service.impl.LiveInfoServiceImpl;
import com.live.service.impl.LiveRoomServiceImpl;
import com.live.service.impl.LiveTypeServiceImpl;
import com.live.vo.LiveInfoVo;
import com.live.vo.LiveSquareVo;
import com.live.vo.LiveTypeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webapi.ResultObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/live")
public class LiveController {

    @Autowired
    private LiveRoomServiceImpl liveRoomService;
    @Autowired
    private LiveInfoServiceImpl liveInfoService;
    @Autowired
    private LiveTypeServiceImpl liveTypeService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @RequestMapping("/create_live_room")
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

    @RequestMapping("/get_private_key")
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
    public ResultObject editLiveRoomInfo(@RequestBody LiveInfoVo liveInfoVo){
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        User user = adminUserDetails.getUser();
        if(user == null){
            return ResultObject.failed("用户信息异常,请先登录");
        }

        System.out.println(liveInfoVo.getTag().get(0));

        liveInfoService.saveLiveRoomInfo(liveInfoVo, user.getId());

        return ResultObject.success("保存信息成功");
    }


    @RequestMapping("/get_all_online_rooms")
    public ResultObject getAllOnlineRooms(){

        List<LiveSquareVo> allLiveRoom = liveRoomService.getAllLiveRoom();

        if(allLiveRoom == null){
            return ResultObject.success("暂时没有直播~");
        }

        return ResultObject.success(allLiveRoom);
    }

    @RequestMapping("/get_all_types")
    public ResultObject getTypesList(){
        QueryWrapper<LiveType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted",0);
        List<LiveType> liveTypes = liveTypeService.getBaseMapper().selectList(queryWrapper);
        List<LiveTypeVo> resList = new ArrayList<>();
        for (int i = 0; i < liveTypes.size(); i++) {
            LiveTypeVo liveTypeVo = new LiveTypeVo();
            liveTypeVo.setType(liveTypes.get(i).getType());
            liveTypeVo.setTypeId(liveTypes.get(i).getId());
            resList.add(liveTypeVo);
        }
        return ResultObject.success(resList,"获取所有类型");
    }

    @RequestMapping("/get_room_by_type")
    public  ResultObject getRoomsByType(@Param("typeId") Long typeId){
        List<LiveSquareVo> liveRoomsByType = liveRoomService.getLiveRoomsByType(typeId);

        return ResultObject.success(liveRoomsByType,"通过类型获取直播间");
    }
}
