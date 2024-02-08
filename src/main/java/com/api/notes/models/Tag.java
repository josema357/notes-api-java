package com.api.notes.models;

import com.api.notes.records.tag.CreateTagDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean status;

    public Tag(CreateTagDTO createTagDTO){
        this.status=true;
        this.name=createTagDTO.name();
    }
}
