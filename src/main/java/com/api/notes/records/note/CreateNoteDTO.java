package com.api.notes.records.note;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateNoteDTO(
        @NotBlank
        @Size(min = 3)
        String title,
        @NotBlank
        String content,
        @NotNull
        Long tag) {
}
