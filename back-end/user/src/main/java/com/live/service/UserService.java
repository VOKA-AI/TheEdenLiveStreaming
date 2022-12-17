package com.live.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.User;
import com.live.vo.UserDetailedInfo;
import com.live.vo.UserInfoSaveVo;
import com.live.vo.UserInfoVo;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public interface UserService extends IService<User> {
    /**
     * 上传头像
     * @param file
     * @param userName
     * @return 上传成功的话，返回新头像的路径
     */
    String uploadPortrait(MultipartFile file, String userName) throws IOException;

    /**
     * 获取用户信息
     * @param username
     * @return 用户信息
     */
    UserInfoVo getUserInfo(String username);

    /**
     * 保存用户个人信息
     * @param userInfoSaveVo
     * @param username
     * @return
     */
    boolean saveUserInfo(UserInfoSaveVo userInfoSaveVo,String username);

    UserInfoVo getUserInfoById(Long userId);

    UserDetailedInfo getUserDetailedInfo(Long userId,String username);


    Long getUserIdByName(String username);

    String getUserNameById(Long userId);

}
