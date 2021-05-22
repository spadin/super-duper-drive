package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES where fileId = #{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES where fileId = #{fileId} AND userId = #{userId}")
    File getFileByFileIdAndUserId(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES where userId = #{userId}")
    List<File> getFilesForUserId(Integer userId);

    @Insert(
        "INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) " +
        "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, RAWTOHEX(#{fileData}))"
    )
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("DELETE FROM FILES where fileId = #{fileId} AND userId = #{userId}")
    boolean deleteFileByFileIdAndUserId(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES where filename = #{filename} AND userId = #{userId}")
    File getFileByFilenameAndUserId(String filename, Integer userId);
}
