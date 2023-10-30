package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNoteAsList(Integer currUserId) {
        return noteMapper.getAllNotes(currUserId);
    }
    public void updateNote(Note newNote) {
        noteMapper.updateNote(newNote);
    }
    public Note getNoteUsingId(Integer noteId) {
        return noteMapper.getOneNote(noteId);
    }
    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void addNote(Note note, int currUserId) {
        note.setUserId(currUserId);
        noteMapper.insertNote(note);
    }

}
