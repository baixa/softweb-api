package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.image.ImageDto;
import com.softweb.api.store.model.dto.license.LicenseDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter @Setter
public abstract class AbstractApplicationGetDto {
    private final Long id;
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private final String logoPath;
    private final LicenseDto license;
    private final Date lastUpdate;
    private final int downloads;
    private final int views;
    private final Set<ImageDto> images;

    public AbstractApplicationGetDto(Application application) {
        this.id = application.getId();
        this.name = application.getName();
        this.shortDescription = application.getShortDescription();
        this.longDescription = application.getLongDescription();
        this.logoPath = application.getLogoPath();
        this.license = new LicenseDto(application.getLicense());
        this.lastUpdate = application.getLastUpdate();
        this.downloads = application.getDownloads();
        this.views = application.getViews();
        images = new HashSet<>();
        application.getImages().stream().map(ImageDto::new).forEach(images::add);
    }
}
