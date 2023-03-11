package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.model.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.Application} entity
 */
@Data
public class ApplicationPostDto implements Serializable {
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private String logo;
    private final LocalDateTime lastUpdate;
    private final int views;
    private final int downloads;
    private User user;
    private License license;
    private Category category;

    public ApplicationPostDto(String name, String shortDescription, String longDescription, License license, Category category) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.license = license;
        this.category = category;
        this.lastUpdate = LocalDateTime.now();
        this.views = 0;
        this.downloads = 0;
    }
}