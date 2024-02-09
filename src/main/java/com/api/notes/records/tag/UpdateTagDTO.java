package com.api.notes.records.tag;

import jakarta.validation.constraints.NotNull;

public record UpdateTagDTO(String name, Boolean status) {
}
