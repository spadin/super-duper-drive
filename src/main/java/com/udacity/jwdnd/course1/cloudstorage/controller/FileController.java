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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String postUpload(
        @RequestParam("fileUpload") MultipartFile fileUpload,
        RedirectAttributes redirectAttributes
    ) {
        User currentUser = this.userService.getCurrentUser();
        String filename = fileUpload.getOriginalFilename();
        boolean fileWithFilenameExists =
            this.fileService.fileWithFilenameAndUserExists(filename, currentUser);
        if (fileWithFilenameExists) {
            redirectAttributes.addFlashAttribute(
                "result",
                ResultFactory.createErrorResult(
                    "A file with the filename \"" +
                    filename +
                    "\" already exists. Try uploading a different file or deleting the file from the app before re-uploading."
                )
            );
        } else {
            this.fileService.createFile(fileUpload, currentUser);
            redirectAttributes.addFlashAttribute(
                "result",
                ResultFactory.createSuccessResult("File uploaded successfully.")
            );
        }
        return "redirect:/result";
    }

    @GetMapping(value = "/download/{fileIdString}")
    public @ResponseBody ResponseEntity<Resource> getFileByFileId(
        @PathVariable String fileIdString
    ) {
        int fileId = Integer.parseInt(fileIdString);
        File file =
            this.fileService.getFileByFileIdAndUser(
                    fileId,
                    this.userService.getCurrentUser()
                );

        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        return ResponseEntity
            .ok()
            .contentLength(Long.parseLong(file.getFileSize()))
            .contentType(MediaType.parseMediaType(file.getContentType()))
            .header(
                "Content-Disposition",
                "attachment; filename=\"" + file.getFileName() + "\""
            )
            .body(resource);
    }

    @GetMapping(value = "/delete/{fileIdString}")
    public String deleteFileByFileId(
        @PathVariable String fileIdString,
        RedirectAttributes redirectAttributes
    ) {
        int fileId = Integer.parseInt(fileIdString);

        this.fileService.deleteFileByFileIdAndUser(
                fileId,
                this.userService.getCurrentUser()
            );

        redirectAttributes.addFlashAttribute(
            "result",
            ResultFactory.createSuccessResult("File deleted successfully.")
        );
        return "redirect:/result";
    }
}
