package com.hjh.nit.pay.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pay")
@Data
public class Pay {
    /**
     * 支付宝交易号
     */
    @Id
    private String trade_no;
    /**
     *订单号
     */
    private String out_trade_no;
    /**
     *订单状态
     */
    private String trade_status;
    /**
     *订单总金额
     */
    private double total_amount;
    /**
     *订单标题
     */
    private String subject;
    /**
     *交易或商品的描述
     */
    private String body;
    /**
     *发起交易请求时间
     */
    private String timestamp;
    /**
     *买家支付宝号
     */
    private String buyer_user_id;
    /**
     *资金渠道
     */
    private String fund_channel;
    /**
     *金额
     */
    private double amount;
}
