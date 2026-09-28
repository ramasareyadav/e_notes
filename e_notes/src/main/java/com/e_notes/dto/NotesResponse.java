package com.e_notes.dto;




import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesResponse {
    private List<NotesDto> notes;
    private Integer pageNo;
    private Integer pageSize;
    private Long totalElement;
    private Integer totalPages;
    private Boolean isFirst;
    private Boolean isLast;
}
