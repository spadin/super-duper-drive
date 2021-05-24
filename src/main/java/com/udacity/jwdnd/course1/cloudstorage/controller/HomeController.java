package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  private final FileService fileService;
  private final NoteService noteService;
  private final CredentialService credentialService;

  public HomeController(
      FileService fileService, NoteService noteService, CredentialService credentialService) {
    this.fileService = fileService;
    this.noteService = noteService;
    this.credentialService = credentialService;
  }

  @GetMapping({"/", "/home"})
  public String getHome(Model model) {
    List<File> files = this.fileService.getFilesForCurrentUser();
    List<Note> notes = this.noteService.getNotesForCurrentUser();
    List<Credential> credentials = this.credentialService.getCredentialsForCurrentUser();

    model.addAttribute("files", files);
    model.addAttribute("notes", notes);
    model.addAttribute("credentials", credentials);
    return "home";
  }
}
