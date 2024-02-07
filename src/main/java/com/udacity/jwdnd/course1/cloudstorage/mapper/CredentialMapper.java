package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentials(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, kei, password, userid) VALUES(#{url}, #{username}, #{kei}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, kei = #{kei}, password = #{password} WHERE credentialid = #{credentialId} AND userid = #{userId}")
    int update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{userId}")
    int delete(int credentialId, int userId);
}
