package com.hjh.nit.pay.model.dto;

import lombok.Data;

@Data
public class PayDto {
    private String out_trade_no;
    private double total_amount;
    private String subject;
    private String body;
}
