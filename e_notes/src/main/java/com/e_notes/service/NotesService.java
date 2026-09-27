package com.e_notes.service;

import com.e_notes.dto.NotesDto;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(NotesDto notesDto);

    public List<NotesDto> getAllNotes();

}
