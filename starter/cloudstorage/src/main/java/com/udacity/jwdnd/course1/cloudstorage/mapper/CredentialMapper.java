package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userid) VALUES (#{url}, #{userName}, #{password}, #{key}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void addCredential(Credentials credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId =#{credentialId} AND userid =#{userId}")
    Credentials getCredential(Long credentialId, Long userId);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credentials getCredentialByUsernameAndUrl(String username, String url);

    @Select("SELECT * FROM CREDENTIALS WHERE userid =#{userId}")
    List<Credentials> getAllCredentials(Long userId);

    @Update("UPDATE CREDENTIALS SET url =#{url}, username =#{userName}, password =#{password}, key =#{key} WHERE credentialId =#{credentialId} AND userId =#{userId}")
    void updateCredential(Credentials credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId} AND userid =#{userId}")
    void deleteCredential(Long credentialId, Long userId);
}
