package com.api.notes.repositories;

import com.api.notes.models.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByStatusTrue(Pageable pagination);
}
