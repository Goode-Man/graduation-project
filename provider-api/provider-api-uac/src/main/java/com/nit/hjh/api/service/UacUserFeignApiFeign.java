package com.nit.hjh.api.service;

import com.nit.hjh.api.service.hystrix.UacUserFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "provider-uac",fallback = UacUserFeignApiHystrix.class)
public interface UacUserFeignApiFeign {

}
