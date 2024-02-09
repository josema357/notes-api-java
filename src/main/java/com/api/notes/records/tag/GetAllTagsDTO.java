package com.api.notes.records.tag;

import com.api.notes.models.Tag;

public record GetAllTagsDTO(Long id, String name, Boolean status) {
    public GetAllTagsDTO(Tag tag){
        this(tag.getId(), tag.getName(), tag.getStatus());
    }
}
