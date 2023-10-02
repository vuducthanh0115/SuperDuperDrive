package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteMapper noteMapper;

    private final CommonService commonService;

    @Override
    public void createNoteOrUpdateNote(Note noteInfo) throws InternalException {

        // 1. Validate the input data
        if (noteInfo.getNoteTitle() == null) {
            throw new InternalException("The title must not be blank");
        }

        // Get the user info of a logged-in user and if it does not exist, throw an exception
        User user = commonService.getUserByUsername();

        // Set value for Note
        Note noteSave = Note.builder()
                .noteId(noteInfo.getNoteId())
                .noteTitle(noteInfo.getNoteTitle())
                .noteDescription(noteInfo.getNoteDescription())
                .userId(user.getUserId())
                .build();

        if (noteInfo.getNoteId() == null) {
            // 2. Save the note
            noteMapper.createNote(noteSave);
        } else {
            // 3. Update the note
            noteMapper.updateNote(noteSave);
        }
    }

    @Override
    public List<Note> getAllNotesByLoggedUser() throws InternalException {

        return noteMapper.getAllNotes(commonService.getUserByUsername().getUserId());
    }

    @Override
    public void deleteNoteByNoteIdAndUserId(Long noteId) throws InternalException {

        noteMapper.deleteNote(noteId, commonService.getUserByUsername().getUserId());
    }
}
