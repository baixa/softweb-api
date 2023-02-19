package com.softweb.api.store.model.dto;

import com.softweb.api.store.model.entities.Application;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link Application} entity
 */
@Data
public class ApplicationDto implements Serializable {
    private final Long id;
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private final String logoPath;
    private final LicenseDto license;
    private final UserDto user;
    private final Date lastUpdate;
    private final int downloads;
    private final int views;
    private final Set<ImageDto> images;

    public ApplicationDto(Application application) {
        this.id = application.getId();
        this.name = application.getName();
        this.shortDescription = application.getShortDescription();
        this.longDescription = application.getLongDescription();
        this.logoPath = application.getLogoPath();
        this.license = new LicenseDto(application.getLicense());
        this.user = new UserDto(application.getUser());
        this.lastUpdate = application.getLastUpdate();
        this.downloads = application.getDownloads();
        this.views = application.getViews();
        images = new HashSet<>();
        application.getImages().stream().map(ImageDto::new).forEach(images::add);
    }
}