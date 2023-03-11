package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.License;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationPutDto {
    private String id;
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private String logo;
    private final LocalDateTime lastUpdate;
    private License license;
    private Category category;

    public ApplicationPutDto(String id, String name, String shortDescription, String longDescription, License license, Category category) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.license = license;
        this.category = category;
        this.lastUpdate = LocalDateTime.now();
    }
}
