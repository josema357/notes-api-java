package com.api.notes.records.note;

import com.api.notes.models.Tag;

import java.time.LocalDateTime;

public record ResponseNoteDTO(
        Long id,
        String title,
        String content,
        Tag tag,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean status) {
}
