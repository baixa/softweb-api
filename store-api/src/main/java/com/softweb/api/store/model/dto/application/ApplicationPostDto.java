package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.entities.License;
import com.softweb.api.store.model.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.Application} entity
 */
@Data
public class ApplicationPostDto implements Serializable {
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private String logo;
    private final Date lastUpdate;
    private final int views;
    private final int downloads;
    private final String licenseCode;
    private final String categoryName;
    private User user;
    private License license;
    private Category category;

    public ApplicationPostDto(String name, String shortDescription, String longDescription, String licenseCode, String categoryName) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.lastUpdate = new Date();
        this.views = 0;
        this.downloads = 0;
        this.licenseCode = licenseCode;
        this.categoryName = categoryName;
    }
}