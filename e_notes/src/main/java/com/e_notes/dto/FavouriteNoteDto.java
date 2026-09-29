package com.e_notes.dto;

import com.e_notes.model.Notes;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteNoteDto {

    private Integer id;

    private NotesDto notes;

    private Integer userId;
}
