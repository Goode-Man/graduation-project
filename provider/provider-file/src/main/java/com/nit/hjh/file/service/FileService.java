package com.nit.hjh.file.service;

import com.nit.hjh.file.model.entity.FilePath;
import com.nit.hjh.file.repository.FilePathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class FileService {

    @Autowired
    private FilePathRepository filePathRepository;

    public void addPath(String path){
        FilePath filePath=new FilePath();
        filePath.setPath(path);
        filePathRepository.save(filePath);
    }

    public List<FilePath> find(){
        return filePathRepository.findAll();
    }

    public List<FilePath> find(String path){
        return filePathRepository.findAllByPath(path);
    }

    public Boolean deletePath(String path){
        return filePathRepository.deletePath(path);
    }

}
