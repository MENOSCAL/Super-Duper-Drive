package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(Note note) {
        return noteMapper.insert(note);
    }

    public int updateNote(Note note) {
        return noteMapper.update(note);
    }

    public int deleteNote(int noteId, int userId) {
        return noteMapper.delete(noteId, userId);
    }

    public List<Note> getUserNotes(int userId) {
        return noteMapper.getNotes(userId);
    }
}
