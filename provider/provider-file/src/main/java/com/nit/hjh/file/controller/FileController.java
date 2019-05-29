package com.nit.hjh.file.controller;

import com.hjh.nit.common.DeleteFileUtil;
import com.hjh.nit.common.PublicUtil;
import com.hjh.nit.common.wrapper.WrapMapper;
import com.hjh.nit.common.wrapper.Wrapper;
import com.nit.hjh.api.dto.FileDTO;
import com.nit.hjh.api.service.FileFeignApiFeign;
import com.nit.hjh.file.model.entity.FilePath;
import com.nit.hjh.file.service.FileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController implements FileFeignApiFeign {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传文件", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "name", value = "文件名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "path", value = "保存位置", required = true, dataType = "String")
    })
    @PostMapping("/upload")
    public boolean upload(MultipartFile file, String name, String path){
        if (file.isEmpty()) {
            System.out.println("文件为空");
            return false;
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        if(PublicUtil.isNotEmpty(name)){
            fileName = name + suffixName; // 新文件名
        }else {
            fileName = UUID.randomUUID() + suffixName; // 新文件名
        }
        File dest = new File(path + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            fileService.addPath(path);
        }
        try {
            file.transferTo(dest);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/search")
    public Wrapper<List<FileDTO>> search(String path) {
        List<FilePath> pathList=fileService.find(path);
        List<FileDTO> list = new ArrayList<>();
        if(pathList.size()>0){
            File paths = new File(path);
            File[] files = paths.listFiles();
            for (int i = 0; i < files.length; i++) {
                    list.add(new FileDTO(files[i].getPath(),files[i].getName()));
            }
        }else {
            List<FilePath> allPathList=fileService.find();
            if(allPathList.size()>0){
                for (int i = 0; i < allPathList.size(); i++) {
                    File paths = new File(allPathList.get(i).getPath());
                    File[] files = paths.listFiles();
                    for(int j =0;i<files.length;j++){
                        list.add(new FileDTO(files[j].getPath(),files[j].getName()));
                    }
                }
            }
        }
        return WrapMapper.ok(list);


    }

    @DeleteMapping("/deleteFile")
    public boolean deleteFile(String name,String path) {
        return DeleteFileUtil.deleteFile(path+name);
    }

    @DeleteMapping("/deletePath")
    public boolean deletePath(String path) {
        return (DeleteFileUtil.delete(path)&&fileService.deletePath(path));
    }
}
