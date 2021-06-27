package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
    	this.noteMapper = noteMapper;
    }

    public int addNote(Note note, Integer userId) {
    	if(null==note.getNoteid()) {
    		return noteMapper.insert(new Note(null, note.getNotetitle(), note.getNotedescription(), userId));
    	}
    	return noteMapper.update(note.getNotetitle(), note.getNotedescription(), note.getNoteid());
    }
    
    public void deleteNote(Integer noteid) {
    	noteMapper.delete(noteid);
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.selectNoteByUserId(userId);
    }
    
    public Note getNoteByNoteId(Integer noteId) {
        return noteMapper.getNote(noteId);
    }
    
    
}
