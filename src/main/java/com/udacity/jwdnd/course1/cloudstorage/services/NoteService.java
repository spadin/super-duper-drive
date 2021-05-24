package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  public final UserService userService;
  public final NoteMapper noteMapper;

  public NoteService(UserService userService, NoteMapper noteMapper) {
    this.userService = userService;
    this.noteMapper = noteMapper;
  }

  public List<Note> getNotesForCurrentUser() {
    return this.getNotesForUserId(this.userService.getCurrentUser().getUserId());
  }

  public List<Note> getNotesForUserId(Integer userId) {
    return this.noteMapper.getNotesForUserId(userId);
  }

  public Integer createOrUpdateNote(Note note) {
    note.setUserId(this.userService.getCurrentUser().getUserId());

    if (note.getNoteId() == null) {
      return this.noteMapper.insert(note);
    } else {
      return this.noteMapper.update(note);
    }
  }

  public void deleteNoteById(Integer noteId) {
    Integer userId = this.userService.getCurrentUser().getUserId();
    this.noteMapper.deleteNoteByNoteId(noteId, userId);
  }
}
