package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES where fileId = #{fileId}")
    File getFileById(int fileId);

    @Select("SELECT * FROM FILES where fileId = #{fileId} AND userId = #{userId}")
    File getFileByFileIdAndUserId(int fileId, int userId);

    @Select("SELECT * FROM FILES where userId = #{userId}")
    List<File> getFilesForUserId(int userId);

    @Insert(
        "INSERT INTO FILES (fileName, contentType, fileSize, userId, fileData) " +
        "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, RAWTOHEX(#{fileData}))"
    )
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);
}
