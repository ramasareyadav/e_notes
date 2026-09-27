package com.e_notes.controller;

import com.e_notes.dto.NotesDto;
import com.e_notes.service.NotesService;
import com.e_notes.util.CommonUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {

    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/savedNotes")
    public ResponseEntity<?> saveNotes(
            @Valid @RequestBody NotesDto notesDto) {

        Boolean saveNotes = notesService.saveNotes(notesDto);

        if (saveNotes) {
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
}