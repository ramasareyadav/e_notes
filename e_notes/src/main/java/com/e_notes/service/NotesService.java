package com.e_notes.service;

import com.e_notes.dto.FavouriteNoteDto;
import com.e_notes.dto.NotesDto;
import com.e_notes.dto.NotesResponse;
import com.e_notes.model.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {

    public Boolean saveNotes(String notes, MultipartFile file);

    public List<NotesDto> getAllNotes();

    NotesDto getNotesById(Integer id);

    Boolean updateNotes(NotesDto notesDto);

    Boolean deleteNotes(Integer id);

    public byte[] downloadFile(FileDetails details) throws Exception;

    FileDetails getFileDetails(Integer id) throws Exception;

    List<NotesDto> getNotesWithPagination(int page, int size);

   // NotesResponse getAllNotesByUser(Integer userId);
   public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize);

   public void softDeleteNotes(Integer id) throws Exception;

   public void restoreNotes(Integer id) throws Exception;

    public List<NotesDto> getUserRecycleBinNotes(Integer userId);

    void hardDeleteNotes(Integer id) throws Exception;

    public void favouriteNotes(Integer noteId)throws Exception;

    public void unFavouriteNotes(Integer noteId)throws Exception;

    List<FavouriteNoteDto> getFavouriteNotes() throws Exception;

   /* void addToFavourite(Integer id) throws Exception;

    void removeFromFavourite(Integer id) throws Exception;

   */// List<NotesDto> getFavouriteNotes(Integer userId) throws Exception;
}
