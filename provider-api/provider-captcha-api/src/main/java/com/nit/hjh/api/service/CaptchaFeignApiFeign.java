package com.nit.hjh.api.service;

import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.api.service.hystrix.CaptchaFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "provider-captcha",fallback = CaptchaFeignApiHystrix.class)
@RequestMapping("/api/captcha")
public interface CaptchaFeignApiFeign {

    @GetMapping("/crateWords")
    Wrapper<String> crateWords();
}
