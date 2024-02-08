package com.api.notes.controllers;

import com.api.notes.repositories.NoteRepository;
import com.api.notes.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
