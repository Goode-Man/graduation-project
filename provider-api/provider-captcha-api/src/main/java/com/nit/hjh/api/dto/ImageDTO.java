package com.nit.hjh.api.dto;

import lombok.Data;

import java.awt.image.BufferedImage;

@Data
public class ImageDTO {
    private BufferedImage image;
    private String text;
}
