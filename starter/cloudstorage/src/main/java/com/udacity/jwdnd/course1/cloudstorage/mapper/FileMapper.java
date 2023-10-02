package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO FILES (fileName, contentType, fileSize, fileData, userid) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})"
    )
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Select("SELECT * FROM FILES WHERE fileName= #{fileName} AND userid= #{userId}")
    Optional<File> getFileByNameAndUserId(String fileName, Long userId);

    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> getAllFiles(Long userId);

    @Select("SELECT * FROM FILES WHERE fileId=#{fileId} AND userid=#{userId}")
    Optional<File> getFileByIdAndUserId(Long fileId, Long userId);

    @Delete("DELETE FROM FILES WHERE fileId=#{fileId} AND userid=#{userId}")
    void deleteFile(Long fileId, Long userId);
}
