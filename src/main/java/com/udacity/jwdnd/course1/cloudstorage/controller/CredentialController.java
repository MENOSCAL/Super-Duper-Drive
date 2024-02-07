package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> credentialCreate(Authentication auth, Credential credential, Model model) {
        HttpHeaders headers = new HttpHeaders();

        int idUser = userService.getUserId(auth);
        if(idUser <= 0) {
            headers.add("Location", "/login");
        } else {
            int rows;
            credential.setUserId(idUser);
            if(credential.getCredentialId() != null) {
                rows = credentialService.updateCredential(credential);
            } else {
                rows = credentialService.createCredential(credential);
            }
            if(rows <= 0) {
                headers.add("Location", "/result?isSuccess=false");
            } else {
                headers.add("Location", "/result?isSuccess=true");
            }
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @GetMapping("/delete")
    public String credentialDelete(@RequestParam("id") int id, Authentication auth) {
        int idUser = userService.getUserId(auth);
        if(idUser <= 0 || id <= 0) {
            return "redirect:/result?isSuccess==false";
        } else {
            int rows = credentialService.deleteCredential(id, idUser);
            if(rows <= 0) {
                return "redirect:/result?isSuccess==false";
            } else {
                return "redirect:/result?isSuccess=true";
            }
        }
    }
}
