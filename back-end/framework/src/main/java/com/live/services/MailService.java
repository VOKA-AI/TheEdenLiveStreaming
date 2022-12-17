package com.live.services;

import org.springframework.stereotype.Component;

@Component
public interface MailService {
    /**
     * 发送邮件
     * @param mail      接受者邮箱
     * @param subject   标题
     * @param content   内容
     */
    void send(String mail,String subject,String content);
}
