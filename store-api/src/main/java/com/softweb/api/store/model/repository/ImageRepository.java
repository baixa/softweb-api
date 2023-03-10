package com.softweb.api.store.model.repository;

import com.softweb.api.store.model.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    long countByPath(String path);
}