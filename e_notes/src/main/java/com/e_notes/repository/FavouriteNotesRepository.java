package com.e_notes.repository;

import com.e_notes.model.FavouriteNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteNotesRepository extends JpaRepository<FavouriteNotes,Integer> {
}
