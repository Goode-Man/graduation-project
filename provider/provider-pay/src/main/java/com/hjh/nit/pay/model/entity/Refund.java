package com.hjh.nit.pay.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "refund")
@Data
public class Refund {
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
     *退款金额
     */
    private double Refund_amount;
    /**
     *退款请求号
     */
    private String out_request_no;
    /**
     *发起退款请求时间
     */
    private String timestamp;
}
