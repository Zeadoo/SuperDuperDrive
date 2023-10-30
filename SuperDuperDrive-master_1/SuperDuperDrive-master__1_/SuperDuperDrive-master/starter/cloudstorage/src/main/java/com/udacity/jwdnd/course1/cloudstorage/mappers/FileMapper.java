package com.udacity.jwdnd.course1.cloudstorage.mappers;

import org.apache.ibatis.annotations.*;
import com.udacity.jwdnd.course1.cloudstorage.models.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.models.File;

import java.sql.Blob;
import java.util.List;


@Mapper
public interface FileMapper {
    
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<FileInfo> getAllFilesFromUserId(Integer userid);

    
    @Select("SELECT * FROM FILES WHERE fileid = #{filedId}")
    File getOneFileInfo(Integer fileId);

    
    @Select("SELECT fileid FROM FILES WHERE ( filename, contenttype, filesize, userid) = ( #{fileName}, #{contentType}, #{fileSize}, #{userId})")
    Integer getFileId(FileInfo fileInfoEntity);

    
    @Insert("INSERT INTO FILES ( filename, contenttype, filesize, userid) VALUES( #{fileName}, #{contentType}, #{fileSize}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    void insert(FileInfo file);

    
    @Update("UPDATE FILES SET filedata= #{fileData} WHERE fileId = #{fileId}")
    void updateFileData(Blob fileData, int fileId);

    
    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(int fileId);

}
