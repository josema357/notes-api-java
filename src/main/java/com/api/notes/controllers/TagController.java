package com.api.notes.controllers;

import com.api.notes.models.Tag;
import com.api.notes.records.tag.CreateTagDTO;
import com.api.notes.records.tag.ResponseTagDTO;
import com.api.notes.repositories.TagRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/tags")
public class TagController {
    private TagRepository tagRepository;
    @Autowired
    public TagController(TagRepository tagRepository){
        this.tagRepository=tagRepository;
    }
    @PostMapping
    public ResponseEntity<ResponseTagDTO> create_tag(
            @RequestBody
            @Valid
            CreateTagDTO createTagDTO,
            UriComponentsBuilder uriComponentsBuilder){
        Tag tag = tagRepository.save(new Tag(createTagDTO));
        ResponseTagDTO response = new ResponseTagDTO(
                tag.getId(),
                tag.getName(),
                tag.getStatus()
        );
        URI uri = uriComponentsBuilder.path("/curso/{id}").buildAndExpand(tag.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
}
