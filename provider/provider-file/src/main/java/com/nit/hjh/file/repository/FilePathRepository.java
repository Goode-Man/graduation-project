package com.nit.hjh.file.repository;

import com.nit.hjh.file.model.entity.FilePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository
public interface FilePathRepository extends JpaRepository<FilePath,Integer> {

    @Override
    <S extends FilePath> S save(S s);

    @Modifying
    @Query(value = "delete from filepath where path=?1",nativeQuery = true)
    boolean deletePath(String Path);

    @Override
    List<FilePath> findAll();

    @Query(value = "select * from filepath where path=?1",nativeQuery = true)
    List<FilePath> findAllByPath(String path);
}
