package com.nit.hjh.api.service;


import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.api.dto.FileDTO;
import com.nit.hjh.api.service.hystrix.FileFeignApiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@FeignClient(value = "provider-file",fallback = FileFeignApiHystrix.class)
@RequestMapping("/api/file")
public interface FileFeignApiFeign {

    @PostMapping("/upload")
    boolean upload(MultipartFile file,String name,String path);

    @GetMapping("/search")
    Wrapper<List<FileDTO>> search(String path);

    @DeleteMapping("/deleteFile")
    boolean deleteFile(String name,String path);

    @DeleteMapping("/deletePath")
    boolean deletePath(String path);
}
