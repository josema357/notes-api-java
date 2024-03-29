package com.api.notes.controllers;

import com.api.notes.models.Note;
import com.api.notes.models.Tag;
import com.api.notes.records.note.CreateNoteDTO;
import com.api.notes.records.note.GetAllNotesDTO;
import com.api.notes.records.note.ResponseNoteDTO;
import com.api.notes.records.note.UpdateNoteDTO;
import com.api.notes.repositories.NoteRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private NoteRepository noteRepository;
    private TagRepository tagRepository;
    @Autowired
    public NoteController(NoteRepository noteRepository, TagRepository tagRepository){
        this.noteRepository=noteRepository;
        this.tagRepository=tagRepository;
    }
    @PostMapping
    public ResponseEntity<ResponseNoteDTO> create_note(
            @RequestBody
            @Valid
            CreateNoteDTO createNoteDTO,
            UriComponentsBuilder uriComponentsBuilder) {
        Optional<Tag> tag = tagRepository.findById(createNoteDTO.tag());
        Note note;
        if(tag.isPresent()){
            Tag tagFound = tag.get();
            note = new Note(createNoteDTO);
            note.setTag(tagFound);
            noteRepository.save(note);
        }else {
            throw new RuntimeException("The tag with id " + createNoteDTO.tag() + " does not exists");
        }
        ResponseNoteDTO responseNoteDTO = new ResponseNoteDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getTag(),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                note.getStatus());
        URI url = uriComponentsBuilder.path("/notes/{id}").buildAndExpand(note.getId()).toUri();
        return ResponseEntity.created(url).body(responseNoteDTO);
    }
    @GetMapping
    public ResponseEntity<List<GetAllNotesDTO>> get_all_notes(
            Pageable pagination,
            @RequestParam(name = "offset", required = false) Integer offset,
            @RequestParam(name = "limit", required = false) Integer limit){
        if(offset != null & limit != null){
            pagination = PageRequest.of(offset, limit);
        }
        Page<Note> notesPage = noteRepository.findByStatusTrue(pagination);
        List<GetAllNotesDTO> notesListDTO = notesPage.getContent().stream().map(GetAllNotesDTO::new).toList();
        return ResponseEntity.ok(notesListDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseNoteDTO> get_note_by_id(@PathVariable Long id){
        Note note = noteRepository.getReferenceById(id);
        ResponseNoteDTO response = new ResponseNoteDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getTag(),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                note.getStatus());
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update_note(@PathVariable Long id, @RequestBody @Valid UpdateNoteDTO updateNoteDTO){
        Optional<Tag> tag = tagRepository.findById(updateNoteDTO.tag());
        Note note;
        if(tag.isPresent()){
            Tag tagFound = tag.get();
            note = noteRepository.getReferenceById(id);
            note.updateNote(updateNoteDTO);
            note.setUpdatedAt(LocalDateTime.now());
            note.setTag(tagFound);
            noteRepository.save(note);
        }else {
            throw new RuntimeException("The tag with id " + updateNoteDTO.tag() + " does not exists");
        }
        ResponseNoteDTO response = new ResponseNoteDTO(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getTag(),
                note.getCreatedAt(),
                note.getUpdatedAt(),
                note.getStatus());
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete_note(@PathVariable Long id){
        Note note = noteRepository.getReferenceById(id);
        noteRepository.delete(note);
        return  ResponseEntity.noContent().build();
    }
}
