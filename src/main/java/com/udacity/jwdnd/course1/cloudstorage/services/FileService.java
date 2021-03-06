package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
    
    /**
     * Load file from DB 
     * 
     * @param multipartFile
     * @param userId
     * @return
     * @throws IOException
     */
    public Integer loadFile(MultipartFile multipartFile, Integer userId) throws IOException {
        File file = new File (null, multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                multipartFile.getSize(), userId, multipartFile.getBytes());
        return fileMapper.insert(file);
    }
    

    /**
     * Get all files from DB
     * 
     * @param userId
     * @return
     */
    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getFilesByUserId(userId);
    }

    public File getFileById(Integer id) {
        return this.fileMapper.getFileByFileId(id);
    }
    
    public boolean isDuplicate(String fileName, Integer userId) {
    	if(fileMapper.getFileByFilenameAndUserId(fileName, userId) != null)
    		return true;
    	else 
    		return false;
    }

    /**
     * Delete file from DB
     * 
     * @param fileId
     * @param userid
     * @return
     */
    public int deleteFile(Integer fileId, Integer userid) {
        return fileMapper.delete(fileId, userid);
    }
}
