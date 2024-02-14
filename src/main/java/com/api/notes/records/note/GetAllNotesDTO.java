package com.api.notes.records.note;

import com.api.notes.models.Note;
import com.api.notes.models.Tag;

import java.time.LocalDateTime;

public record GetAllNotesDTO(
        Long id,
        String title,
        String content,
        Tag tag,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean status) {
    public GetAllNotesDTO(Note note){
        this(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getTag(),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                note.getStatus());
    }
}
