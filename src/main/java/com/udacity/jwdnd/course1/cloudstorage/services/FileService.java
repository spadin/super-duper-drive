package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final UserService userService;
    private final FileMapper fileMapper;

    public FileService(UserService userService, FileMapper fileMapper) {
        this.userService = userService;
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesForCurrentUser() {
        return this.getFilesForUserId(this.userService.getCurrentUser().getUserId());
    }

    public List<File> getFilesForUserId(Integer userId) {
        return this.fileMapper.getFilesForUserId(userId);
    }

    public Integer createFile(MultipartFile fileUpload, User user) {
        File file = new File(null, fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                             String.valueOf(fileUpload.getSize()), user.getUserId(), getFileData(fileUpload));

        return this.fileMapper.insert(file);
    }

    public File getFileByFileIdAndUser(Integer fileId, User user) {
        return this.fileMapper.getFileByFileIdAndUserId(fileId, user.getUserId());
    }

    protected byte[] getFileData(MultipartFile fileUpload) {
        try {
            return fileUpload.getBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public boolean deleteFileByFileIdAndUser(Integer fileId, User user) {
        return this.fileMapper.deleteFileByFileIdAndUserId(fileId, user.getUserId());
    }

    public boolean fileWithFilenameAndUserExists(String filename, User user) {
        return (this.fileMapper.getFileByFilenameAndUserId(filename, user.getUserId()) != null);
    }
}
