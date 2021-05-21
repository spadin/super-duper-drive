package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultFactory;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {

    private FileService fileService;
    private UserService userService;

    public UploadController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String postUpload(
        @RequestParam("fileUpload") MultipartFile fileUpload,
        RedirectAttributes redirectAttributes
    ) {
        this.fileService.createFile(fileUpload, getCurrentUser());
        redirectAttributes.addFlashAttribute(
            "result",
            ResultFactory.createSuccessResult("File uploaded successfully.")
        );
        return "redirect:/result";
    }

    @GetMapping(value = "/file/{fileIdString}")
    public @ResponseBody
    ResponseEntity<Resource> getFileByFileId(
        @PathVariable String fileIdString
    ) {
        int fileId = Integer.parseInt(fileIdString);
        File file =
            this.fileService.getFileByFileIdAndUserId(
                fileId,
                getCurrentUser().getUserId()
            );

        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        return ResponseEntity
            .ok()
            .contentLength(Long.parseLong(file.getFileSize()))
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .header("Content-Disposition", "attachment; filename=\"" + file.getFileName() + "\"")
            .body(resource);
    }

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder
            .getContext()
            .getAuthentication();
        String username = (String) authentication.getPrincipal();
        return this.userService.getUser(username);
    }
}
