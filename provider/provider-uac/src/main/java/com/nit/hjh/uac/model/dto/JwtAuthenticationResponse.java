package com.nit.hjh.uac.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 7205403410242986046L;
    private final String token;



}
