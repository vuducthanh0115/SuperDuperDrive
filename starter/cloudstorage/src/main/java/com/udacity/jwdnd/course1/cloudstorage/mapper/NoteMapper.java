package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int createNote(Note note);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId} AND userId = #{userId}")
    Note getNoteDetail(Long noteId, Long userId);

    @Select("SELECT * FROM NOTES WHERE userId =#{userId}")
    List<Note> getAllNotes(Long userId);

    @Update("UPDATE NOTES SET noteTitle =#{noteTitle}, noteDescription=#{noteDescription} WHERE noteId =#{noteId} AND userId =#{userId}")
    int updateNote(Note note);

    @Select("SELECT * FROM NOTES WHERE noteId =#{noteId} AND userId =#{userId}")
    Note getNote(Long noteId, Long userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId} AND userId =#{userId}")
    boolean deleteNote(Long noteId, Long userId);
}
