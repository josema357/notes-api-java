package com.api.notes.models;

import com.api.notes.records.note.CreateNoteDTO;
import com.api.notes.records.note.UpdateNoteDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    private Boolean status = true;

    public Note (CreateNoteDTO createNoteDTO){
        this.title = createNoteDTO.title();
        this.content = createNoteDTO.content();
    }
    public void updateNote (UpdateNoteDTO updateNoteDTO){
        if(updateNoteDTO.title()!=null){
            this.title = updateNoteDTO.title();
        }
        if(updateNoteDTO.content()!=null){
            this.content = updateNoteDTO.content();
        }
        if(updateNoteDTO.status()!=null){
            this.status = updateNoteDTO.status();
        }
    }
}
