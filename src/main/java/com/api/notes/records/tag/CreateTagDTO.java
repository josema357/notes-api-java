package com.api.notes.records.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTagDTO(
        @NotBlank
        @Size(min = 3, max = 255)
        String name) {
}
