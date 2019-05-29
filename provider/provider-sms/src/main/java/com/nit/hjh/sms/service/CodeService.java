package com.nit.hjh.sms.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeService {
    public String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(ran.nextInt(10)).toString());
        }
        return code.toString();
    }

}
