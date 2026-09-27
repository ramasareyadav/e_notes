package com.e_notes.controller;

import com.e_notes.dto.NotesDto;
import com.e_notes.service.NotesService;
import com.e_notes.util.CommonUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    // ================= SAVE NOTES =================

    @PostMapping(
            value = "/savedNotes",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> saveNotes(
            @RequestPart("notes") String notes,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        Boolean saved = notesService.saveNotes(notes, file);

        if (saved) {
            return CommonUtil.createBuildResponse(
                    "Notes saved successfully",
                    HttpStatus.CREATED
            );
        }

        return CommonUtil.createErrorResponseMessage(
                "Notes not saved",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // ================= GET ALL NOTES =================

    @GetMapping("/allNotes")
    public ResponseEntity<?> getAllNotes() {

        List<NotesDto> allNotes = notesService.getAllNotes();

        if (CollectionUtils.isEmpty(allNotes)) {
            return ResponseEntity.noContent().build();
        }

        return CommonUtil.createBuildResponse(
                allNotes,
                HttpStatus.OK
        );
    }

    // ================= GET NOTES BY ID =================

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotesById(
            @PathVariable Integer id) {

        NotesDto notes = notesService.getNotesById(id);

        return CommonUtil.createBuildResponse(
                notes,
                HttpStatus.OK
        );
    }

    // ================= UPDATE NOTES =================

    @PutMapping("/updateNotes")
    public ResponseEntity<?> updateNotes(
            @Valid @RequestBody NotesDto notesDto) {

        Boolean updated = notesService.updateNotes(notesDto);

        if (updated) {
            return CommonUtil.createBuildResponse(
                    "Notes updated successfully",
                    HttpStatus.OK
            );
        }

        return CommonUtil.createErrorResponseMessage(
                "Notes not updated",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // ================= DELETE NOTES =================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotes(
            @PathVariable Integer id) {

        Boolean deleted = notesService.deleteNotes(id);

        if (deleted) {
            return CommonUtil.createBuildResponse(
                    "Notes deleted successfully",
                    HttpStatus.OK
            );
        }

        return CommonUtil.createErrorResponseMessage(
                "Notes not deleted",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}