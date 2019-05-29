package com.nit.hjh.file.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "filepath")
public class FilePath implements Serializable {

    private static final long serialVersionUID = 4099214163829687022L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fpid;
    /**
     * 文件夹名
     */
    private String Path;
}
