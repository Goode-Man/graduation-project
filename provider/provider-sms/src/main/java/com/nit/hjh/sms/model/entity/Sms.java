package com.nit.hjh.sms.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "sms")
public class Sms {
    @Id
    private String bizid;

    private String phone;

    private String templateCode;

    private String sendtime;
}
