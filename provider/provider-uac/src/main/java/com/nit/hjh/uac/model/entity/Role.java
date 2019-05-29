package com.nit.hjh.uac.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name="role")
public class Role {
    @Id
    private int rid;

    private String rname;
}
