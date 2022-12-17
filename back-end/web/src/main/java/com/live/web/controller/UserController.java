package com.live.web.controller;

import com.live.entry.User;

import com.live.models.vos.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.live.services.impls.IUsersServiceImpl;
import com.live.services.impls.MailServiceImpl;
import com.live.utils.CodeUtils;
import webapi.ResultObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUsersServiceImpl usersService;
    private final MailServiceImpl mailService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    public UserController(IUsersServiceImpl usersService, MailServiceImpl mailService) {
        this.usersService = usersService;
        this.mailService = mailService;
    }

    @PostMapping("/sendMailCode")
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
        return flag ? ResultObject.success("注册成功") : ResultObject.failed("注册失败");
    }


    @PostMapping("/login")
    public ResultObject login(String name,String password){
        String token = usersService.login(name, password);

        if(token == null) {
            return ResultObject.validateFailed("用户名或账号错误");
        }

        // 将 JWT 传递回客户端
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("username",name) ;
        long id = usersService.getCurrentLoginUser(name).getId();
        tokenMap.put("userId", String.valueOf(id));

        return ResultObject.success(tokenMap);
    }

    @PostMapping("/test")
    public Integer test(@RequestBody HashMap<String,String> hashMap, HttpServletRequest request){
        System.out.println("直播");
        System.out.println(hashMap.toString());
        return null;
    }


    //获取当前用户登录信息



}
