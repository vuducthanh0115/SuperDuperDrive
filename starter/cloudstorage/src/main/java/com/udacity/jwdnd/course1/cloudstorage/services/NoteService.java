package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;

import java.util.List;

public interface NoteService {
    void createNoteOrUpdateNote(Note note) throws InternalException;

    void deleteNoteByNoteIdAndUserId(Long noteId) throws InternalException;

    List<Note> getAllNotesByLoggedUser() throws InternalException;

}
