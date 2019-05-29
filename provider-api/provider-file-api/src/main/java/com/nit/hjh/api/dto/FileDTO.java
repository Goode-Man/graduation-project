package com.nit.hjh.api.dto;

import lombok.Data;

@Data
public class FileDTO {
    private String path;
    private String name;

    public FileDTO(String path, String name) {
        this.path = path;
        this.name = name;
    }
}
