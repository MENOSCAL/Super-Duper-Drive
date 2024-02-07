package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String homeView(Authentication auth, Model model) {
        int idUser = userService.getUserId(auth);
        if(idUser <= 0) {
            return "login";
        }

        model.addAttribute("fileList", fileService.getUserFiles(idUser));

        model.addAttribute("noteList", noteService.getUserNotes(idUser));

        model.addAttribute("credentialList", credentialService.getUserCredentials(idUser));

        model.addAttribute("note", new Note());

        model.addAttribute("credential", new Credential());

        return "home";
    }
}
