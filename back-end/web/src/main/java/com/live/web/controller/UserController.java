package com.live.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.live.entry.User;

import com.live.models.vos.RegisterVo;
import com.live.service.impl.UserServiceImpl;
import com.live.vo.UserDetailedInfo;
import com.live.vo.UserInfoSaveVo;
import com.live.vo.UserInfoVo;
import com.live.exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.live.services.impls.IUsersServiceImpl;
import com.live.services.impls.MailServiceImpl;
import com.live.utils.CodeUtils;
import org.springframework.web.multipart.MultipartFile;
import com.live.webapi.ResultObject;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Api(tags = "用户")
public class UserController {

    private final IUsersServiceImpl usersService;
    private final MailServiceImpl mailService;
    private final UserServiceImpl userService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    public UserController(IUsersServiceImpl usersService, MailServiceImpl mailService, UserServiceImpl userService) {
        this.usersService = usersService;
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping("/sendMailCode")
    @ApiOperation("发送邮箱")
    public ResultObject sendMailCode(String mail, HttpSession httpSession){
        if(mail == null){
            return ResultObject.failed("mail不能为空");
        }

        //判断邮箱是否被注册

        String subject = "邮箱验证码，请妥善保管";
        String mailCode = CodeUtils.generateText();
        mailService.send(mail,subject,mailCode);

        //将生成的验证码存入session中
        httpSession.setAttribute("mailCode",mailCode);
        return ResultObject.success("发送验证码成功");
    }


    @PostMapping("/registerByMail")
    @ApiOperation("通过邮箱注册")
    public ResultObject registerByMail(RegisterVo registerVo, HttpSession httpSession){

        //判断邮箱验证码
        String mailCode = (String) httpSession.getAttribute("mailCode");

        if(mailCode == null){
            return ResultObject.failed("请先获取验证码");
        }

        if(!mailCode.equals(registerVo.getAuthCode())){
            return ResultObject.failed("验证码输入错误");
        }

        //判断用户名是否被用过了
        User user = new User();
        user.setName(registerVo.getUserName());
        user.setPwd(registerVo.getPassword());
        user.setMail(registerVo.getMail());

        //进行用户注册
        boolean flag = usersService.register(user);
        return flag ? ResultObject.success("注册成功") : ResultObject.failed("用户名重复");
    }


    @PostMapping("/login")
    @ApiOperation("登录")
    public ResultObject login(String name,String password){
        //token
        String token = null;
        try{
            token = usersService.login(name, password);
        } catch (ApiException e){
            return ResultObject.validateFailed("用户名或账号错误");
        } catch (UsernameNotFoundException e){
            return ResultObject.failed("用户名错误");
        }


        if(token == null) {
            return ResultObject.validateFailed("用户名或账号错误");
        }

        // 将 JWT 传递回客户端
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("name",name) ;
        User user = usersService.getCurrentLoginUser(name);
        long id = user.getId();
        tokenMap.put("id", String.valueOf(id));
        tokenMap.put("portrait",user.getPortrait());

        return ResultObject.success(tokenMap);
    }


    @PostMapping("/judge_token_status")
    public ResultObject judgeTokenStatus(){
        return ResultObject.success("token信息正常");
    }




    @PostMapping("/upload_portrait")
    public ResultObject changePortrait(@RequestPart(value = "file") MultipartFile file, Principal principal) throws IOException {

        if(file == null || file.isEmpty()){
            return ResultObject.failed("请选择上传头像");
        }

        System.out.println("用户信息(Principal)" + principal.getName());
        String newUrl = "";
        try {
            newUrl = userService.uploadPortrait(file, principal.getName());
        } catch (IOException e){
            return ResultObject.failed("发生未知错误,上传头像失败");
        }

        JSONObject object = new JSONObject();
        object.put("url",newUrl);

        return ResultObject.success("上传头像成功",object.toJSONString());
    }

    @GetMapping("/get_user_info")
    public ResultObject getUserInfo(Principal principal){
        String username =  principal.getName();

        UserInfoVo userInfo = userService.getUserInfo(username);

        return ResultObject.success(userInfo,"获取用户信息成功");
    }


    @GetMapping("/get_user_info_by_id_or_name")
    public ResultObject getUserInfoById(@RequestParam(value = "userId",required = false) Long userId,@RequestParam(value = "username",required = false) String username){
        if(userId == null && username == null){
            return ResultObject.failed("参数错误");
        }

        UserDetailedInfo userDetailedInfo = userService.getUserDetailedInfo(userId, username);
        if(userDetailedInfo == null) return ResultObject.failed("用户不存在");


        return ResultObject.success(userDetailedInfo,"获取用户信息成功");
    }

    @PostMapping("/save_user_info")
    public ResultObject saveUserInfo(@RequestBody UserInfoSaveVo userInfoSaveVo, Principal principal){
        if(userInfoSaveVo.getNickname() == null){
            return ResultObject.failed("用户名不能为空");
        }
        boolean  b = userService.saveUserInfo(userInfoSaveVo, principal.getName());

        return b ?  ResultObject.success("保存信息成功") : ResultObject.failed("保存信息失败");
    }


    @GetMapping("/get_user_info_by_id")
    @ApiOperation("根据id获取用户信息")
    public ResultObject getUserInfoById(Long userId){
        if(userId == null){
            return ResultObject.failed("请输入userId");
        }

        UserInfoVo userInfoById = userService.getUserInfoById(userId);
        if(userInfoById == null){
            return ResultObject.failed("用户信息不存在");
        }

        return ResultObject.success(userInfoById,"获取用户信息成功");
    }

}
