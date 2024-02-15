package com.api.notes.records.note;

public record UpdateNoteDTO(String title, String content, Long tag, Boolean status) {
}
