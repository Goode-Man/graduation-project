package com.nit.hjh.mail.model.entity;

import lombok.Data;

@Data
public class MailBean {
    private String recipient;   //邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容
}
