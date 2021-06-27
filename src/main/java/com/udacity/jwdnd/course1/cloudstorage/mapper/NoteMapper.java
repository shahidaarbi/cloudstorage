package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(Integer noteid);
    
	@Select("select * from NOTES as n where n.userid=#{id}")
	public List<Note> selectNoteByUserId(Integer id);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);
    
    @Insert("Update NOTES set notetitle=#{notetitle}, notedescription=#{notedescription} where noteid=#{noteid}")
    int update(String notetitle, String notedescription, Integer noteid);
    
    @Select("Delete FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer noteid);
}
