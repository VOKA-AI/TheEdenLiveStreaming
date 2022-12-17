package com.live.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.component.JwtAuthenticationTokenFilter;
import com.live.consant.BroadcastStatus;
import com.live.entry.LiveAccount;
import com.live.entry.LiveInfo;
import com.live.entry.LiveTag;
import com.live.entry.User;
import com.live.mapper.LiveAccountMapper;
import com.live.service.AmazonServiceImpl;
import com.live.service.LiveRoomService;
import com.live.services.impls.IUsersServiceImpl;
import com.live.utils.JwtTokenUtil;
import com.live.vo.LiveSquareVo;
import com.live.consant.LiveConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.live.webapi.ResultCode;
import com.live.webapi.ResultObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
    @Autowired
    LiveRecordingServiceImpl liveRecordingService;
    @Autowired
    private AmazonServiceImpl amazonService;

    @Value("${recording.minTime}")
    private Integer minRecordingTime;


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
        liveAccount.setOnlineNumber(0l);

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
        System.out.println("+++++++++++++++++++++进入开播回调方法++++++++++++++++++++++");

        String username = jwtTokenUtil.getUserNameFromToken(token);


        if(username == null) {
            return false;
        }

        User user = usersService.getAdminByUsername(username);

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);
        BroadcastStatus status = new BroadcastStatus();

        //只有编辑了直播间信息才能开播
        ResultObject editInfo = liveInfoService.getEditInfo(user.getId());
        //设置开播时间
        status.setTime(System.currentTimeMillis());

        if(liveAccount == null || editInfo.getCode() == 500){
            status.setResultCode(ResultCode.PUBLISH_FAIL);
            LiveConstant.statusMap.put(username,status);

            LiveConstant.statusMap.forEach((k,v)-> System.out.println("key:" + k + " value:" + v));
            return false;
        }

        //判断用户最近5分钟是否开播过
        if(LiveConstant.statusMap.get(username) != null){
            System.out.println("-------------------------判断用户最近5分钟开播过-------------------------------------------" + LiveConstant.statusMap.get(username).toString());
            //说明开播最近5分钟内掉线了
            BroadcastStatus status1 = LiveConstant.statusMap.get(username);
            System.out.println("-------------------开播时间戳------------------------" + (System.currentTimeMillis() - status1.getTime()));

            //判断距离上次开播是否超过5分钟
            System.out.println("默认有效时间-------------------->>>>>>>>>>>>>>" + minRecordingTime * 60000.0);
            if(System.currentTimeMillis() - status1.getTime() < minRecordingTime * 60000.0){
                System.out.println("----------------------------没有超过5分钟----------------------------------------");

                if( status1.getResultCode().getCode() == 503){
                    System.out.println("----------------------------状态合法，修改数据库----------------------------------------");
                    //说明上次推流是成功的，直接将数据库状态修改即可
                    liveAccount.setState(1);
                    baseMapper.updateById(liveAccount);
                    return true;
                }
            }
        }



        if(liveAccount.getLivePath().equals(livePath)){

            //这里不对状态进行修改，而是在一键开播的时候进行修改
            status.setResultCode(ResultCode.SUCCESS);
            LiveConstant.statusMap.put(username, status);
            LOGGER.info("-----------------------------map--------------------------------------");
            LiveConstant.statusMap.forEach((k,v)-> System.out.println("key:" + k + " value:" + v));
            return true;
        }
        return false;
    }

    @Override
    public Boolean judgeUnPublish(String livePath) {

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("live_path",livePath);
        queryWrapper.eq("state",1);

        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);
        if(liveAccount == null) return false;

        long userId = liveAccount.getUserId();
        //判断推流时间是否超过5分钟
        BroadcastStatus status = LiveConstant.statusMap.get(usersService.getUserNameById(userId));
        System.out.println("-------------------停播时间戳------------------------" + (System.currentTimeMillis() - status.getTime()));
        if(status != null && System.currentTimeMillis() - status.getTime() > minRecordingTime * 60000.0){//超过5分钟，直接删除

            //如果下播之后
            System.out.println("---------------------------超过5分钟，移除对应用户的map--------------------------------------");

            //直播超过5分钟，才进行直播历史记录
            Date date = DateUtil.date(System.currentTimeMillis());

            QueryWrapper<LiveInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("is_deleted",0);
            LiveInfo liveInfo = liveInfoService.getBaseMapper().selectOne(wrapper);

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<保存直播历史记录>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            //需要一个方法获取infoid
            liveRecordingService.createRecording(new Timestamp(status.getTime()),new Timestamp(date.getTime()),liveInfo.getId(),userId);

            //移除map里面的元素
            LiveConstant.statusMap.remove(usersService.getUserNameById(userId));
        }

        //推流时间没有超过5分钟
        //修改为暂时下播状态
        status.setTime(System.currentTimeMillis());
        status.setResultCode(ResultCode.UNPUBLISH_TEMP);
        LiveConstant.statusMap.put(usersService.getUserNameById(userId),status);
        //将直播间里的状态修改为0
        liveAccount.setState(0);
        if(baseMapper.updateById(liveAccount) > 0){
            LOGGER.info("修改直播状态成功");
            return true;
        }

        LOGGER.info("修改直播状态失败");
        return false;
    }

    @Override
    public ResultObject getAllLiveRoom(Page<LiveSquareVo> page) {
        //从数据库中找state为1的直播间
//        QueryWrapper<LiveAccount>  queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("state",1);
//        List<LiveAccount> list = baseMapper.selectList(queryWrapper);
        IPage<LiveSquareVo> allRooms = baseMapper.getAllRooms(page);
        //找对应的tags
        for (LiveSquareVo record : allRooms.getRecords()) {
            long infoId = record.getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            record.setTags(liveTags);
        }

        return ResultObject.success(allRooms,"获取广场信息成功");
    }

    @Override
    public List<LiveSquareVo> getRoomsOnTop() {
        List<LiveSquareVo> roomsOnTop = baseMapper.getRoomsOnTop();

        for (int i = 0; i < roomsOnTop.size(); i++) {
            //找对应的tags
            long infoId = roomsOnTop.get(i).getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            roomsOnTop.get(i).setTags(liveTags);
        }

        return roomsOnTop;
    }

    @Override
    public ResultObject getLiveRoomsByType(long typeId,Page<LiveSquareVo> page) {
        IPage<LiveSquareVo> roomsByTypeId = baseMapper.getRoomsByTypeId(typeId,page);

        for (LiveSquareVo record : roomsByTypeId.getRecords()) {
            long infoId = record.getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            record.setTags(liveTags);
        }

        //找对应的user
//        for (int i = 0; i < roomsByTypeId.size(); i++) {
//            //找对应的tags
//            long infoId = roomsByTypeId.get(i).getInfoId();
//            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
//            liveTagQueryWrapper.eq("info_id",infoId);
//            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
//            roomsByTypeId.get(i).setTags(liveTags);
//        }

        return ResultObject.success(roomsByTypeId,"根据类型获取信息成功");
    }

    @Override
    public ResultObject getLiveRoomsStatus(Long roomId) {
        if(roomId == null){
            return ResultObject.failed("roomId不能为空");
        }

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",roomId);
        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);
        if(liveAccount == null){
            return ResultObject.failed("房间不存在");
        }
        if(liveAccount.getState() == 1){
            return ResultObject.success(1,"正在直播");
        }
        return ResultObject.success(0,"没有直播");
    }

    @Override
    public LiveSquareVo getLiveRoomsByUserID(long userId) {
        LiveSquareVo roomByUserID = baseMapper.getRoomByUserID(userId);
        if(roomByUserID == null){
            return null;
        }
        long infoId = roomByUserID.getInfoId();
        QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
        liveTagQueryWrapper.eq("info_id",infoId);
        List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
        roomByUserID.setTags(liveTags);

        return roomByUserID;
    }

    @Override
    public String uploadCover(MultipartFile file,Long userId) throws IOException {
        String url = amazonService.uploadFile(file);

        //修改对应account的url
        QueryWrapper<LiveAccount> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId );
        LiveAccount liveAccount = this.getBaseMapper().selectOne(wrapper);
        liveAccount.setCoverUrl(url);
        this.getBaseMapper().updateById(liveAccount);

        return url;
    }

    @Override
    public Boolean watchCallback(String livePath) {
        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("live_path",livePath);
        LiveAccount liveAccount = this.getBaseMapper().selectOne(queryWrapper);

        if(liveAccount == null) return false;//如果直播间不存在，就不能收看直播

        String username = usersService.getUserNameById(liveAccount.getUserId());
        System.out.println("-------------------username----------------"+username);
        System.out.println("-----------------------------map--------------------------------------");
        LiveConstant.statusMap.forEach((k,v)-> System.out.println("key:" + k + "value:" + v));
        //判断是否点了一键开播
        if(!LiveConstant.statusMap.containsKey(username)){
            //如果map不包含username 或者 map对应username的直播状态为500，就拒绝收看
            return false;
        }

        if (liveAccount.getState() == 0) {
            //如果直播间为未开播状态，说明还没有点一键开播
            return false;
        }

        liveAccount.setOnlineNumber(liveAccount.getOnlineNumber() + 1);

        int i = this.getBaseMapper().updateById(liveAccount);

        return i > 0;
    }

    @Override
    public Boolean stopWatchCallback(String livePath) {
        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("live_path",livePath);
        LiveAccount liveAccount = this.getBaseMapper().selectOne(queryWrapper);


        if(liveAccount.getOnlineNumber() > 0){
            liveAccount.setOnlineNumber(liveAccount.getOnlineNumber() - 1);
        }


        int i = this.getBaseMapper().updateById(liveAccount);

        return i > 0;
    }


    @Override
    public boolean save(LiveAccount entity) {
        return super.save(entity);
    }







}
