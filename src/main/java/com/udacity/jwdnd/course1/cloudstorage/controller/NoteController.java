package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultFactory;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

  private final NoteService noteService;

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @PostMapping
  public String createOrUpdateNote(
      @ModelAttribute Note note, RedirectAttributes redirectAttributes) {
    this.noteService.createOrUpdateNote(note);
    redirectAttributes.addFlashAttribute(
        "result", ResultFactory.createSuccessResult("Note successfully created or updated."));
    return "redirect:/result";
  }

  @GetMapping("/delete/{noteIdString}")
  public String deleteFileByFileId(
      @PathVariable String noteIdString, RedirectAttributes redirectAttributes) {
    Integer noteId = Integer.parseInt(noteIdString);

    this.noteService.deleteNoteById(noteId);

    redirectAttributes.addFlashAttribute(
        "result", ResultFactory.createSuccessResult("Note deleted successfully."));
    return "redirect:/result";
  }
}
