package com.api.notes.controllers;

import com.api.notes.models.Tag;
import com.api.notes.records.tag.CreateTagDTO;
import com.api.notes.records.tag.GetAllTagsDTO;
import com.api.notes.records.tag.ResponseTagDTO;
import com.api.notes.records.tag.UpdateTagDTO;
import com.api.notes.repositories.TagRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
                tag.getStatus());
        URI uri = uriComponentsBuilder.path("/tags/{id}").buildAndExpand(tag.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
    @GetMapping
    public ResponseEntity<List<GetAllTagsDTO>> get_all_tag(
            Pageable pagination,
            @RequestParam(name = "offset", required = false) Integer offset,
            @RequestParam(name = "limit", required = false) Integer limit){
        if (offset != null && limit != null) {
            pagination = PageRequest.of(offset, limit);
        }
        Page<Tag> tagsPage = tagRepository.findByStatusTrue(pagination);
        List<GetAllTagsDTO> tagsListDTO = tagsPage.getContent().stream().map(GetAllTagsDTO::new).toList();
        return ResponseEntity.ok(tagsListDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> get_tag_by_id(@PathVariable Long id){
        Tag tag = tagRepository.getReferenceById(id);
        ResponseTagDTO data = new ResponseTagDTO((tag.getId()), tag.getName(), tag.getStatus());
        return ResponseEntity.ok(data);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update_tag(@PathVariable Long id, @RequestBody @Valid UpdateTagDTO updateTagDTO){
        Tag tag = tagRepository.getReferenceById(id);
        tag.updateTag(updateTagDTO);
        ResponseTagDTO response = new ResponseTagDTO(
                tag.getId(),
                tag.getName(),
                tag.getStatus());
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete_tag(@PathVariable Long id){
        Tag tag = tagRepository.getReferenceById(id);
        tagRepository.delete(tag);
        return ResponseEntity.noContent().build();
    }
}
