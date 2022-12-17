package com.live.services;

import com.baomidou.mybatisplus.extension.service.IService;
import com.live.entry.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface IUserService extends IService<User> {
    /**
     * 根据用户名获取用户，真正查询数据库
     */
    User getAdminByUsername(String username);

    /**
     * 注册功能
     */
    boolean register(User user);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 修改密码
     */
//    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取当前登录用户
     * @return
     */
    User getCurrentLoginUser(String username);

    /**
     * 获取当前登录用户id
     * @return
     */
    Long getCurrentUserId();


}
