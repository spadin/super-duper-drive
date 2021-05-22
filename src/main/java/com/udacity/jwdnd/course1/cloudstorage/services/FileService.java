package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesForUserId(int userId) {
        return this.fileMapper.getFilesForUserId(userId);
    }

    public int createFile(MultipartFile fileUpload, User user) {
        File file = new File(
            null,
            fileUpload.getOriginalFilename(),
            fileUpload.getContentType(),
            String.valueOf(fileUpload.getSize()),
            user.getUserId(),
            getFileData(fileUpload)
        );

        return this.fileMapper.insert(file);
    }

    public File getFileByFileIdAndUser(int fileId, User user) {
        return this.fileMapper.getFileByFileIdAndUserId(fileId, user.getUserId());
    }

    protected byte[] getFileData(MultipartFile fileUpload) {
        try {
            return fileUpload.getBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public boolean deleteFileByFileIdAndUser(int fileId, User user) {
        return this.fileMapper.deleteFileByFileIdAndUserId(fileId, user.getUserId());
    }

    public boolean fileWithFilenameAndUserExists(String filename, User user) {
        return (
            this.fileMapper.getFileByFilenameAndUserId(filename, user.getUserId()) != null
        );
    }
}
