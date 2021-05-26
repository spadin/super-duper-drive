package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Result;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultFactory;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequestMapping("/login")
public class LoginController {

  @GetMapping
  public String loginView(HttpServletRequest request, Model model) {
    Result result = getResult(request);
    model.addAttribute("result", result);
    return "login";
  }

  public Result getResult(HttpServletRequest request) {
    Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

    if (inputFlashMap != null && inputFlashMap.get("result") != null) {
      return (Result) inputFlashMap.get("result");
    } else {
      return ResultFactory.createEmptyResult();
    }
  }
}
