package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoteMapper {
  @Insert(
      "INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
  @Options(useGeneratedKeys = true, keyProperty = "noteId")
  Integer insert(Note note);

  @Update(
      "UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId} AND userId = #{userId}")
  Integer update(Note note);

  @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
  List<Note> getNotesForUserId(Integer userId);

  @Delete("DELETE NOTES WHERE noteId = #{noteId} AND userId = #{userId}")
  void deleteNoteByNoteId(Integer noteId, Integer userId);
}
