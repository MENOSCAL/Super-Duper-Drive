package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileById(int fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFiles(int userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata, filepath) VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData}, #{filePath})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId} AND userid = #{userId}")
    int delete(int fileId, int userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId} AND userid = #{userId}")
    File getFile(int fileId, int userId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} AND filename = #{filename}")
    List<File> getFileByFilename(int userId, String filename);
}
