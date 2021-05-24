package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CredentialMapper {
  @Insert(
      "INSERT INTO CREDENTIALS (credentialId, url, username, key, password, userId) VALUES (#{credentialId}, #{url}, #{key}, #{password}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "credentialId")
  Integer insert(Credential credential);

  @Update(
      "UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialId = #{credentialId} AND userId = #{userId}")
  Integer update(Credential credential);

  @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
  List<Credential> getCredentialsForUserId(Integer userId);

  @Delete("DELETE CREDENTIALS WHERE credentialId = #{credentialId} AND userId = #{userId}")
  void deleteCredentialByCredentialId(Integer credentialId, Integer userId);
}
