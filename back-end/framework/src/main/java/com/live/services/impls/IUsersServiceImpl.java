package com.live.services.impls;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.live.models.AdminUserDetails;
import com.live.services.IUserService;
import com.live.utils.JwtTokenUtil;
import com.live.entry.User;
import com.live.exception.ApiException;
import com.live.exception.Asserts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.live.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Data
public class IUsersServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
    private static Logger LOGGER = LoggerFactory.getLogger(IUsersServiceImpl.class);


    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public IUsersServiceImpl(PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public User getAdminByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",username);

        List<User> users = getBaseMapper().selectList(queryWrapper);
        if(users != null && users.size() > 0){
            return users.get(0);
        }

        throw new UsernameNotFoundException("用户名错误");
    }

    @Override
    public boolean register(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",user.getName());
        long count = this.getBaseMapper().selectCount(queryWrapper);
        if (count > 0){
            return false;
        }

        user.setNickname(user.getName());//默认昵称为账号
        String encodePassword = passwordEncoder.encode(user.getPwd());//密码加密
        user.setPwd(encodePassword);
        user.setPortrait("https://s3.ap-east-1.amazonaws.com/srs-live-web-storage-hk/portrait/1668511789045-default.png");
        return save(user);
    }

    /**
     * 根据用户名从数据库查询用户，附带资源后，验证密码和账号是否禁用，
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public String login(String username, String password) throws ApiException {
        String token = null;

            UserDetails userDetails = loadUserByUsername(username);

            //验证密码
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                Asserts.fail("密码不正确");
            }
            token = jwtTokenUtil.generateToken(userDetails);


        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User admin = getAdminByUsername(username);
        if(admin  != null){
            return new AdminUserDetails(admin);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public User getCurrentLoginUser(String username) {

        return getAdminByUsername(username);
    }

    @Override
    public Long getCurrentUserId() {
        return 0l;
    }

    @Override
    public String getUserNameById(Long id) {
        System.out.println("-------------------userId---------------------" + id);
        User user = baseMapper.selectById(id);
        if(user == null){
            return null;
        }

        return user.getName();
    }

}
