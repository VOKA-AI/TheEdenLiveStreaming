package com.live.services.impls;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.live.services.MailService;

import javax.annotation.Resource;

@Service
@Data
public class MailServiceImpl implements MailService {
    private static Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    //SpringBoot自动配置好了mailSender和mailProperties
    @Resource
    private MailSender mailSender;

    @Resource
    private MailProperties mailProperties;

    @Override
    public void send(String mail, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String from = mailProperties.getProperties().get("from");
        if (StringUtils.isBlank(from)) {
            throw new RuntimeException("未配置邮件发送人");
        }
        // 邮件发送人必须和配置的授权过的邮箱一样
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        LOGGER.info(String.valueOf(simpleMailMessage));
        mailSender.send(simpleMailMessage);
    }
}
