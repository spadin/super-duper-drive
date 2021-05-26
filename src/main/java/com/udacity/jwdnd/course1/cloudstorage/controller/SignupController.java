package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.ResultFactory;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
public class SignupController {

  private final UserService userService;

  public SignupController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public String signupView() {
    return "signup";
  }

  @PostMapping
  public String signupUser(
      @ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
    String signupError = null;

    if (!userService.isUsernameAvailable(user.getUsername())) {
      signupError = "The username already exists.";
    }

    if (signupError == null) {
      Integer rowsAdded = userService.createUser(user);
      if (rowsAdded < 0) {
        signupError = "There was an error signing you up. Please try again.";
      }
    }

    if (signupError == null) {
      redirectAttributes.addFlashAttribute(
          "result", ResultFactory.createSuccessResult("Successfully signed up!"));
      return "redirect:/login";
    } else {
      model.addAttribute("signupError", signupError);
    }

    return "signup";
  }
}
