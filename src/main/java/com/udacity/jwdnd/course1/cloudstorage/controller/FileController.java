package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.AppConstant;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/file")
public class FileController {
    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/{id_}/{filename_}")
    public void fileView(@PathVariable int id_, @PathVariable String filename_, HttpServletResponse response, Authentication auth)throws IOException {
        int idUser = userService.getUserId(auth);
        if(idUser > 0 && fileService.isFileExists(id_, idUser)){
            response.setContentType(fileService.getFileById(id_).getContentType());
            response.setHeader("Content-Length", fileService.getFileById(id_).getFileSize());
            response.setHeader("Content-Disposition", "attachment; filename=" + fileService.getFileById(id_).getFilename());

            InputStream resource = new FileInputStream(new java.io.File(fileService.getFileById(id_).getFilePath()));
            IOUtils.copy(resource, response.getOutputStream());
            IOUtils.closeQuietly(resource);
            IOUtils.closeQuietly(response.getOutputStream());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Void> fileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication auth, Model model) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        if(fileUpload.isEmpty() || (fileUpload.getSize() > AppConstant.MAX_SIZE_FILE)) {
            headers.add("Location", "/result?isSuccess=false");
        } else {
            int idUser = userService.getUserId(auth);
            if(idUser <= 0) {
                headers.add("Location", "/login");
            } else {
                if(!fileService.getFilenameFiles(idUser, fileUpload.getOriginalFilename()).isEmpty()) {
                    headers.add("Location", "/result?isSuccess=false");
                } else {
                    int rowsAdded = fileService.createFile(fileUpload, idUser);
                    if(rowsAdded <= 0) {
                        headers.add("Location", "/result?isSuccess=false");
                    } else {
                        //headers.add("Location", "/home");
                        headers.add("Location", "/result?isSuccess=true");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @GetMapping("/delete")
    public String fileDelete(@RequestParam("id") int id, Authentication auth) {
        int idUser = userService.getUserId(auth);
        if(idUser <= 0 || id <= 0) {
            return "redirect:/result?isSuccess==false";
        } else {
            int rows = fileService.deleteFile(id, idUser);
            if(rows <= 0) {
                return "redirect:/result?isSuccess==false";
            } else {
                return "redirect:/result?isSuccess=true";
            }
        }
    }
}
