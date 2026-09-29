package com.e_notes.repository;

import com.e_notes.dto.NotesResponse;
import com.e_notes.model.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Integer> {
    Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);

    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

    List<Notes> findByIsDeletedTrueAndDeletedOnBefore(LocalDateTime expiryDate);

    List<Notes> findByCreatedByAndIsFavouriteTrueAndIsDeletedFalse(Integer userId);
}
