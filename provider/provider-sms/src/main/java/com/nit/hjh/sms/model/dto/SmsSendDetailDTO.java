package com.nit.hjh.sms.model.dto;

import lombok.Data;

@Data
public class SmsSendDetailDTO {
    private String SendDate;
    private String SendStatus;
    private String ReceiveDate;
    private String ErrCode;
    private String TemplateCode;
    private String Content;
    private String PhoneNum;
}
