package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping()
    public ResponseEntity<Void> indexView() {
        HttpHeaders headers = new HttpHeaders();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken) {
            headers.add("Location", "/login");
        } else {
            headers.add("Location", "/home");
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
