package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final java.io.File saveDir;

    public FileService(FileMapper fileMapper, @Value("${save-dir}") java.io.File saveDir) {
        this.fileMapper = fileMapper;
        this.saveDir = saveDir;
        saveDir.mkdirs();
    }

    public boolean isFileExists(int fileId, int userId) {
        return fileMapper.getFile(fileId, userId) != null;
    }

    public int createFile(MultipartFile fileUpload, int userId) throws IOException {
        String pathFile = createFileDisk(fileUpload);

        File file = new File();
        file.setUserId(userId);
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(Long.toString(fileUpload.getSize()));
        file.setFilePath(pathFile);
        file.setFileData(null);
        return fileMapper.insert(file);
    }

    public int deleteFile(int fileId, int userId) {
        return fileMapper.delete(fileId, userId);
    }

    public List<File> getUserFiles(int userId) {
        return fileMapper.getFiles(userId);
    }

    public File getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }

    public List<File> getFilenameFiles(int userId, String filename) {
        return fileMapper.getFileByFilename(userId, filename);
    }

    public String createFileDisk(MultipartFile fileUpload) throws IOException {
        InputStream ints = fileUpload.getInputStream();
        java.io.File destination = new java.io.File(saveDir, format("%s-%s", UUID.randomUUID(), fileUpload.getOriginalFilename()));
        FileOutputStream outs = new FileOutputStream(destination);
        IOUtils.copy(ints, outs);
        IOUtils.closeQuietly(ints);
        IOUtils.closeQuietly(outs);
        return destination.getPath();
    }
    /*
    public int createFileArray(MultipartFile fileUpload, int userId) throws IOException {
        InputStream ints = fileUpload.getInputStream();
        int nRead;
        byte[] data = new byte[1024];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        while((nRead = ints.read(data, 0, data.length)) != -1 ){
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileData = buffer.toByteArray();
        File file = new File();
        file.setUserId(userId);
        file.setFilename(fileUpload.getOriginalFilename());
        file.setContentType(fileUpload.getContentType());
        file.setFileSize(Long.toString(fileUpload.getSize()));

        file.setFileData(fileData);
        return fileMapper.insert(file);
    }
    */
}
