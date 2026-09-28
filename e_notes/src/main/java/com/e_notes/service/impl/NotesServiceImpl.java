package com.e_notes.service.impl;

import com.e_notes.dto.NotesDto;
import com.e_notes.dto.NotesResponse;
import com.e_notes.exception.ResourceNotFoundException;
import com.e_notes.model.Category;
import com.e_notes.model.FileDetails;
import com.e_notes.model.Notes;
import com.e_notes.repository.CategoryRepository;
import com.e_notes.repository.FileRepository;
import com.e_notes.repository.NotesRepository;
import com.e_notes.service.NotesService;
import com.e_notes.util.Validation;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class NotesServiceImpl implements NotesService {

    private final NotesRepository notesRepository;
    private final ModelMapper modelMapper;
    private final Validation validation;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    private final FileRepository fileRepository;

    @Value("${file.upload.path}")
    private String uploadPath;

    public NotesServiceImpl(
            NotesRepository notesRepository,
            ModelMapper modelMapper,
            Validation validation,
            CategoryRepository categoryRepository,
            ObjectMapper objectMapper, FileRepository fileRepository) {

        this.notesRepository = notesRepository;
        this.modelMapper = modelMapper;
        this.validation = validation;
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
        this.fileRepository = fileRepository;
    }

    // ================= SAVE NOTES =================

    @Override
    public Boolean saveNotes(String notesJson, MultipartFile file) {

        try {

            // 1. JSON String -> NotesDto
            NotesDto notesDto =
                    objectMapper.readValue(notesJson, NotesDto.class);

            // 2. Validation
            validation.notesValidation(notesDto);


            // 3. Check category
            Long categoryId =
                    notesDto.getCategory().getId();

            Category category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Category not found with id: "
                                            + categoryId
                            )
                    );

            // 4. DTO -> Entity
            Notes notesEntity =
                    modelMapper.map(notesDto, Notes.class);

            // 5. Set actual Category entity
            notesEntity.setCategory(category);

            // 6. Audit fields
            notesEntity.setCreatedBy(
                    notesDto.getCreatedBy()
            );

            notesEntity.setCreatedOn(
                    LocalDateTime.now()
            );

//            // 7. File information
//            if (file != null && !file.isEmpty()) {
//
//                notesEntity.setFileName(
//                        file.getOriginalFilename()
//                );
//
//                notesEntity.setFileType(
//                        file.getContentType()
//               );
//            }

            // 8. Save Notes
            FileDetails fileDetails = saveFileDetails(file);

            if (!ObjectUtils.isEmpty(fileDetails)) {
                notesEntity.setFileDetails(fileDetails);
            } else {
                notesEntity.setFileDetails(null);
            }

            Notes savedNotes =
                    notesRepository.save(notesEntity);

            // 9. Check save result
            return savedNotes != null;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to save notes: " + e.getMessage(),
                    e
            );
        }
    }

    private FileDetails saveFileDetails(MultipartFile file) {

        try {

            // 1. Check file
            if (ObjectUtils.isEmpty(file) || file.isEmpty()) {
                throw new IllegalArgumentException(
                        "Please select a file"
                );
            }

            // 2. Get original file name
            String originalFile = file.getOriginalFilename();

            if (originalFile == null || originalFile.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "File name is missing"
                );
            }

            // 3. Get extension
            String extension = FilenameUtils
                    .getExtension(originalFile)
                    .toLowerCase();

            // 4. Allowed extensions
            List<String> allowedExtensions = List.of(
                    "pdf",
                    "jpg",
                    "jpeg",
                    "png"
            );

            // 5. Validate extension
            if (!allowedExtensions.contains(extension)) {
                throw new IllegalArgumentException(
                        "Invalid file type. Only PDF, JPG, JPEG and PNG files are allowed"
                );
            }

            FileDetails fileDetails = new FileDetails();

            fileDetails.setOriginalFileName(originalFile);

            // 6. Display name
            fileDetails.setDisplayName(
                    getDisplayName(originalFile)
            );

            // 7. Generate unique file name
            String randomString =
                    UUID.randomUUID().toString();

            String uploadFileName =
                    randomString + "." + extension;

            fileDetails.setUploadFileName(
                    uploadFileName
            );

            // 8. File size
            fileDetails.setFileSize(
                    file.getSize()
            );

            // 9. Create upload directory
            File saveFile = new File(uploadPath);

            if (!saveFile.exists()) {

                boolean created = saveFile.mkdirs();

                if (!created) {
                    throw new IOException(
                            "Unable to create upload directory: "
                                    + uploadPath
                    );
                }
            }

            // 10. Create complete path
            Path storePath = Paths.get(
                    uploadPath,
                    uploadFileName
            );

            fileDetails.setPath(
                    storePath.toString()
            );

            // 11. Copy file
            long upload = Files.copy(
                    file.getInputStream(),
                    storePath
            );

            if (upload <= 0) {
                throw new IOException(
                        "File upload failed"
                );
            }

            // 12. Save details in DB
            FileDetails savedFile =
                    fileRepository.save(fileDetails);

            if (savedFile == null) {

                // Delete uploaded file if DB save fails
                Files.deleteIfExists(storePath);

                throw new RuntimeException(
                        "File details could not be saved"
                );
            }

            return savedFile;

        } catch (IOException e) {

            throw new RuntimeException(
                    "File upload failed: " + e.getMessage(),
                    e
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to save file: " + e.getMessage(),
                    e
            );
        }
    }

    private String getDisplayName(String originalFile) {
        String extension = FilenameUtils.getExtension(originalFile);
        String fileName = FilenameUtils.removeExtension(originalFile);
        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        fileName = fileName + "." + extension;
        return fileName;
    }

    // ================= GET ALL NOTES =================

    @Override
    public List<NotesDto> getAllNotes() {

        return notesRepository.findAll()
                .stream()
                .map(notes ->
                        modelMapper.map(
                                notes,
                                NotesDto.class
                        )
                )
                .toList();
    }

    // ================= GET NOTES BY ID =================

    @Override
    public NotesDto getNotesById(Integer id) {

        Notes notes = notesRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notes not found with id: " + id
                        )
                );

        return modelMapper.map(
                notes,
                NotesDto.class
        );
    }

    // ================= UPDATE NOTES =================

    @Override
    public Boolean updateNotes(NotesDto notesDto) {

        // 1. Check ID
        if (notesDto.getId() == null) {

            throw new IllegalArgumentException(
                    "Notes id is required for update"
            );
        }

        // 2. Validation
        validation.notesValidation(notesDto);

        // 3. Find existing Notes
        Notes existingNotes = notesRepository
                .findById(notesDto.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notes not found with id: "
                                        + notesDto.getId()
                        )
                );

        // 4. Get Category ID
        Long categoryId =
                notesDto.getCategory().getId();

        // 5. Find Category
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + categoryId
                        )
                );

        // 6. Update title
        existingNotes.setTitle(
                notesDto.getTitle()
        );

        // 7. Update description
        existingNotes.setDescription(
                notesDto.getDescription()
        );

        // 8. Update category
        existingNotes.setCategory(category);

        // 9. Update audit fields
        existingNotes.setUpdatedBy(
                notesDto.getUpdatedBy()
        );

        existingNotes.setUpdatedOn(
                LocalDateTime.now()
        );

        // 10. Save
        Notes updatedNotes =
                notesRepository.save(existingNotes);

        return updatedNotes != null;
    }

    // ================= DELETE NOTES =================

    @Override
    public Boolean deleteNotes(Integer id) {

        Notes notes = notesRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notes not found with id: " + id
                        )
                );

        notesRepository.delete(notes);

        return true;
    }

    @Override
    public byte[] downloadFile(FileDetails details) throws Exception {
        // FileDetails fileDetails = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("file is not available "));

        FileInputStream inputStream = new FileInputStream(details.getPath());
        return StreamUtils.copyToByteArray(inputStream);
    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {

        FileDetails fileDet = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("file is not available "));
        return fileDet;

    }

    @Override
    public List<NotesDto> getNotesWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Notes> notesPage = notesRepository.findAll(pageable);

        return notesPage.getContent()
                .stream()
                .map(notes ->
                        modelMapper.map(notes, NotesDto.class))
                .toList();
    }
    @Override
    public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
        {

            Pageable pageable = PageRequest.of(0, 10);

            Page<Notes> pageNotes =
                    notesRepository.findByCreatedBy(userId, pageable);

            List<NotesDto> notesDto =
                    pageNotes.getContent()
                            .stream()
                            .map(n -> modelMapper.map(n, NotesDto.class))
                            .toList();

            return NotesResponse.builder()
                    .notes(notesDto)
                    .pageNo(pageNotes.getNumber())
                    .pageSize(pageNotes.getSize())
                    .totalElement(pageNotes.getTotalElements())
                    .totalPages(pageNotes.getTotalPages())
                    .isFirst(pageNotes.isFirst())
                    .isLast(pageNotes.isLast())
                    .build();
        }
    }
}