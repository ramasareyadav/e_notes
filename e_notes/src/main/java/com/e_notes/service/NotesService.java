package com.e_notes.service;

import com.e_notes.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file);

    public List<NotesDto> getAllNotes();

    NotesDto getNotesById(Integer id);

    Boolean updateNotes(NotesDto notesDto);

    Boolean deleteNotes(Integer id);

}
