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

  public Integer createOrUpdateNote(Note note)
      throws DataTooLargeException, AlreadyExistsException {
    note.setUserId(this.userService.getCurrentUser().getUserId());
    return handleCreateOrUpdateNote(note);
  }

  public void deleteNoteById(Integer noteId) {
    Integer userId = this.userService.getCurrentUser().getUserId();
    this.noteMapper.deleteNoteByNoteId(noteId, userId);
  }

  protected Integer insertNote(Note note) throws AlreadyExistsException {
    if (this.noteMapper.getNoteByNoteTitleAndUserId(note) != null) {
      throw new AlreadyExistsException("Note already exists.");
    } else {
      return this.noteMapper.insert(note);
    }
  }

  protected Integer updateNote(Note note) {
    return this.noteMapper.update(note);
  }

  protected Integer handleCreateOrUpdateNote(Note note)
      throws AlreadyExistsException, DataTooLargeException {
    try {
      if (note.getNoteId() == null) {
        return insertNote(note);
      } else {
        return updateNote(note);
      }
    } catch (RuntimeException e) {
      if (e.getMessage().contains("Value too long for column")) {
        throw new DataTooLargeException(e.getMessage());
      } else {
        throw e;
      }
    }
  }
}
