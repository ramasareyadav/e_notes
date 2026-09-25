package com.e_notes.repository;

import com.e_notes.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByIsActiveTrue();

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
    List<Category> findByIsDeletedFalse();

    List<Category> findByIsActiveTrueAndIsDeletedFalse();
}
