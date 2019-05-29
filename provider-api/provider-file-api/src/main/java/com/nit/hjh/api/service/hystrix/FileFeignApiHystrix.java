package com.nit.hjh.api.service.hystrix;


import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.api.dto.FileDTO;
import com.nit.hjh.api.service.FileFeignApiFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Component
public class FileFeignApiHystrix implements FileFeignApiFeign {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileFeignApiHystrix.class);
    @Override
    public boolean upload(MultipartFile file, String name, String path) {
        return false;
    }

    @Override
    public Wrapper<List<FileDTO>> search(String path) {
        return null;
    }

    @Override
    public boolean deleteFile(String name, String path) {
        return false;
    }

    @Override
    public boolean deletePath(String path) {
        return false;
    }
}
