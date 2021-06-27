package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential getCredential(Integer credentialid);
    
	@Select("select * from CREDENTIALS as n where n.userid=#{id}")
	public List<Credential> selectCredentialByUserId(Integer id);

    @Insert("INSERT INTO CREDENTIALS (url, username,key,password, userid) VALUES(#{url}, #{username},#{key}, #{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential Credential);
    
    @Insert("Update CREDENTIALS set url=#{url}, username=#{username}, key =#{key}, password=#{password} where credentialid=#{credentialid}")
    int update(String url, String username,String key, String password,  Integer credentialid);
    
    @Select("Delete FROM CREDENTIALS WHERE credentialid=#{credentialid}")
    void delete(Integer credentialid);
}
