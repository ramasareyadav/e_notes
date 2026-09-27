package com.e_notes.service.impl;

import com.e_notes.dto.NotesDto;
import com.e_notes.exception.ResourceNotFoundException;
import com.e_notes.model.Category;
import com.e_notes.model.Notes;
import com.e_notes.repository.CategoryRepository;
import com.e_notes.repository.NotesRepository;
import com.e_notes.service.NotesService;
import com.e_notes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final ModelMapper modelMapper;
    private final Validation validation;
    private final CategoryRepository categoryRepository;

    public NotesServiceImpl(
            NotesRepository notesRepository,
            ModelMapper modelMapper,
            Validation validation, CategoryRepository categoryRepository) {

        this.notesRepository = notesRepository;
        this.modelMapper = modelMapper;
        this.validation = validation;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean saveNotes(NotesDto notesDto) {

        // DTO validation
        validation.notesValidation(notesDto);

        // Create entity
        Notes notes = modelMapper.map(notesDto, Notes.class);

        // Find category
        Category category = categoryRepository
                .findById(notesDto.getCategory().getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + notesDto.getCategory().getId()
                        )
                );

        notes.setCategory(category);

        // Audit fields
        notes.setCreatedBy(notesDto.getCreatedBy());
        notes.setCreatedOn(LocalDateTime.now());

        // Save
        Notes savedNotes = notesRepository.save(notes);

        if (savedNotes == null) {
            throw new RuntimeException("Notes not saved");
        }

        return true;
    }
    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepository.findAll()
                .stream()
                .map(notes -> modelMapper.map(notes, NotesDto.class))
                .toList();
    }
}