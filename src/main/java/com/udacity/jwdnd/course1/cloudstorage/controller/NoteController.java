package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/note")
public class NoteController {
    private UserService userService;
    private NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping("/save")
    public ResponseEntity<Void> noteCreate(Authentication auth, Note note, Model model) {
        HttpHeaders headers = new HttpHeaders();

        int idUser = userService.getUserId(auth);
        if(idUser <= 0) {
            headers.add("Location", "/login");
        } else {
            int rowsAdded;
            note.setUserId(idUser);
            if(note.getNoteId() != null) {
                rowsAdded = noteService.updateNote(note);
            } else {
                rowsAdded = noteService.createNote(note);
            }
            if(rowsAdded <= 0) {
                headers.add("Location", "/result?isSuccess=false");
            } else {
                headers.add("Location", "/result?isSuccess=true");
            }
        }
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @GetMapping("/delete")
    public String noteDelete(@RequestParam("id") int id, Authentication auth) {
        int idUser = userService.getUserId(auth);
        if(idUser <= 0 || id <= 0) {
            return "redirect:/result?isSuccess==false";
        } else {
            int rows = noteService.deleteNote(id, idUser);
            if(rows <= 0) {
                return "redirect:/result?isSuccess==false";
            } else {
                return "redirect:/result?isSuccess=true";
            }
        }
    }
}
