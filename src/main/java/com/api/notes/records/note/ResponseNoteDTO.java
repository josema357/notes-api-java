package com.api.notes.records.note;

import java.time.LocalDateTime;

public record ResponseNoteDTO(
        Long id,
        String title,
        String content,
        Long tag,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean status) {
}
