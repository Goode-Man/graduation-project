package com.nit.hjh.api.service.hystrix;

import com.hjh.nit.common.wrapper.WrapMapper;
import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.api.service.CaptchaFeignApiFeign;

public class CaptchaFeignApiHystrix implements CaptchaFeignApiFeign {
    @Override
    public Wrapper<String> crateWords() {
        return WrapMapper.error();
    }
}
