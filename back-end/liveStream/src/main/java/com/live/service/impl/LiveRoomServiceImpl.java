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
        //????????????????????????????????????????????????
        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        long count = this.baseMapper.selectCount(queryWrapper);
        //????????????????????????
        if(count > 0){
            return false;
        }
        //???????????????????????????
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
        System.out.println("+++++++++++++++++++++????????????????????????++++++++++++++++++++++");

        String username = jwtTokenUtil.getUserNameFromToken(token);


        if(username == null) {
            return false;
        }

        User user = usersService.getAdminByUsername(username);

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);
        BroadcastStatus status = new BroadcastStatus();

        //??????????????????????????????????????????
        ResultObject editInfo = liveInfoService.getEditInfo(user.getId());
        //??????????????????
        status.setTime(System.currentTimeMillis());

        if(liveAccount == null || editInfo.getCode() == 500){
            status.setResultCode(ResultCode.PUBLISH_FAIL);
            LiveConstant.statusMap.put(username,status);

            LiveConstant.statusMap.forEach((k,v)-> System.out.println("key:" + k + " value:" + v));
            return false;
        }

        //??????????????????5?????????????????????
        if(LiveConstant.statusMap.get(username) != null){
            System.out.println("-------------------------??????????????????5???????????????-------------------------------------------" + LiveConstant.statusMap.get(username).toString());
            //??????????????????5??????????????????
            BroadcastStatus status1 = LiveConstant.statusMap.get(username);
            System.out.println("-------------------???????????????------------------------" + (System.currentTimeMillis() - status1.getTime()));

            //????????????????????????????????????5??????
            System.out.println("??????????????????-------------------->>>>>>>>>>>>>>" + minRecordingTime * 60000.0);
            if(System.currentTimeMillis() - status1.getTime() < minRecordingTime * 60000.0){
                System.out.println("----------------------------????????????5??????----------------------------------------");

                if( status1.getResultCode().getCode() == 503){
                    System.out.println("----------------------------??????????????????????????????----------------------------------------");
                    //?????????????????????????????????????????????????????????????????????
                    liveAccount.setState(1);
                    baseMapper.updateById(liveAccount);
                    return true;
                }
            }
        }



        if(liveAccount.getLivePath().equals(livePath)){

            //???????????????????????????????????????????????????????????????????????????
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
        //??????????????????????????????5??????
        BroadcastStatus status = LiveConstant.statusMap.get(usersService.getUserNameById(userId));
        System.out.println("-------------------???????????????------------------------" + (System.currentTimeMillis() - status.getTime()));
        if(status != null && System.currentTimeMillis() - status.getTime() > minRecordingTime * 60000.0){//??????5?????????????????????

            //??????????????????
            System.out.println("---------------------------??????5??????????????????????????????map--------------------------------------");

            //????????????5????????????????????????????????????
            Date date = DateUtil.date(System.currentTimeMillis());

            QueryWrapper<LiveInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("is_deleted",0);
            LiveInfo liveInfo = liveInfoService.getBaseMapper().selectOne(wrapper);

            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<????????????????????????>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            //????????????????????????infoid
            liveRecordingService.createRecording(new Timestamp(status.getTime()),new Timestamp(date.getTime()),liveInfo.getId(),userId);

            //??????map???????????????
            LiveConstant.statusMap.remove(usersService.getUserNameById(userId));
        }

        //????????????????????????5??????
        //???????????????????????????
        status.setTime(System.currentTimeMillis());
        status.setResultCode(ResultCode.UNPUBLISH_TEMP);

        //?????????????????????????????????0
        liveAccount.setState(0);
        if(baseMapper.updateById(liveAccount) > 0){
            LOGGER.info("????????????????????????");
            return true;
        }

        LOGGER.info("????????????????????????");
        return false;
    }

    @Override
    public ResultObject getAllLiveRoom(Page<LiveSquareVo> page) {
        //??????????????????state???1????????????
//        QueryWrapper<LiveAccount>  queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("state",1);
//        List<LiveAccount> list = baseMapper.selectList(queryWrapper);
        IPage<LiveSquareVo> allRooms = baseMapper.getAllRooms(page);
        //????????????tags
        for (LiveSquareVo record : allRooms.getRecords()) {
            long infoId = record.getInfoId();
            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
            liveTagQueryWrapper.eq("info_id",infoId);
            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
            record.setTags(liveTags);
        }

        return ResultObject.success(allRooms,"????????????????????????");
    }

    @Override
    public List<LiveSquareVo> getRoomsOnTop() {
        List<LiveSquareVo> roomsOnTop = baseMapper.getRoomsOnTop();

        for (int i = 0; i < roomsOnTop.size(); i++) {
            //????????????tags
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

        //????????????user
//        for (int i = 0; i < roomsByTypeId.size(); i++) {
//            //????????????tags
//            long infoId = roomsByTypeId.get(i).getInfoId();
//            QueryWrapper<LiveTag> liveTagQueryWrapper = new QueryWrapper<>();
//            liveTagQueryWrapper.eq("info_id",infoId);
//            List<LiveTag> liveTags = liveTagService.getBaseMapper().selectList(liveTagQueryWrapper);
//            roomsByTypeId.get(i).setTags(liveTags);
//        }

        return ResultObject.success(roomsByTypeId,"??????????????????????????????");
    }

    @Override
    public ResultObject getLiveRoomsStatus(Long roomId) {
        if(roomId == null){
            return ResultObject.failed("roomId????????????");
        }

        QueryWrapper<LiveAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",roomId);
        LiveAccount liveAccount = baseMapper.selectOne(queryWrapper);
        if(liveAccount == null){
            return ResultObject.failed("???????????????");
        }
        if(liveAccount.getState() == 1){
            return ResultObject.success(1,"????????????");
        }
        return ResultObject.success(0,"????????????");
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

        //????????????account???url
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

        if(liveAccount == null) return false;//????????????????????????????????????????????????

        String username = usersService.getUserNameById(liveAccount.getUserId());
        System.out.println("-------------------username----------------"+username);
        System.out.println("-----------------------------map--------------------------------------");
        LiveConstant.statusMap.forEach((k,v)-> System.out.println("key:" + k + "value:" + v));
        //??????????????????????????????
        if(!LiveConstant.statusMap.containsKey(username)){
            //??????map?????????username ?????? map??????username??????????????????500??????????????????
            return false;
        }

        if (liveAccount.getState() == 0) {
            //??????????????????????????????????????????????????????????????????
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
