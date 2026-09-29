package com.e_notes.schedular;

import com.e_notes.model.Notes;
import com.e_notes.repository.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesSchedular {

    private final NotesRepository notesRepository;


    @Value("${notes.recycle-bin.retention-days:30}")
    private int retentionDays;

    public NotesSchedular(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }


    @Scheduled(cron = "0 0 2 * * *")
    public void deleteNoteSchedular() {
        LocalDateTime expiryDate = LocalDateTime.now().minusDays(30);

        List<Notes> expiredNotes = notesRepository.findByIsDeletedTrueAndDeletedOnBefore(expiryDate);

        if (!expiredNotes.isEmpty()) {

            notesRepository.deleteAll(expiredNotes);

            System.out.println(
                    expiredNotes.size()
                            + " notes permanently deleted from recycle bin."
            );

        } else {

            System.out.println(
                    "No expired notes found in recycle bin."
            );
        }
    }
}
