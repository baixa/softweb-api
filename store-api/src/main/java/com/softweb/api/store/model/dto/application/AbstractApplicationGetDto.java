package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.category.CategoryDto;
import com.softweb.api.store.model.dto.license.LicenseDto;
import com.softweb.api.store.model.dto.user.UserGetDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public abstract class AbstractApplicationGetDto {
    private final Long id;
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private final String logoPath;
    private final LicenseDto license;
    private final CategoryDto category;
    private final LocalDateTime lastUpdate;
    private final int downloads;
    private final int views;
    private final UserGetDto user;

    public AbstractApplicationGetDto(Application application) {
        this.id = application.getId();
        this.name = application.getName();
        this.shortDescription = application.getShortDescription();
        this.longDescription = application.getLongDescription();
        this.logoPath = application.getLogoPath();
        this.license = new LicenseDto(application.getLicense());
        this.lastUpdate = application.getLastUpdate().plusHours(3);
        this.downloads = application.getDownloads();
        this.views = application.getViews();
        this.category = new CategoryDto(application.getCategory());
        this.user = new UserGetDto(application.getUser());
    }
}
