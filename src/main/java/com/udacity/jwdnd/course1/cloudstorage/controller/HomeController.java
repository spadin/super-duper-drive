package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  private final UserService userService;
  private final FileService fileService;
  private final NoteService noteService;

  private final Logger logger = LoggerFactory.getLogger(HomeController.class);

  public HomeController(UserService userService, FileService fileService, NoteService noteService) {
    this.userService = userService;
    this.fileService = fileService;
    this.noteService = noteService;
  }

  @GetMapping({"/", "/home"})
  public String getHome(Principal principal, Model model) {
    List<File> files = this.fileService.getFilesForCurrentUser();
    List<Note> notes = this.noteService.getNotesForCurrentUser();

    model.addAttribute("files", files);
    model.addAttribute("notes", notes);
    return "home";
  }
}
