package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.image.ImageDto;
import com.softweb.api.store.model.dto.installer.InstallerDto;
import com.softweb.api.store.model.dto.user.UserDefaultGetDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.Application} entity
 */
@Getter @Setter
public class ApplicationDefaultGetDto extends AbstractApplicationGetDto implements Serializable {
    private final UserDefaultGetDto user;
    private final Set<ImageDto> images;
    private final Set<InstallerDto> installers;

    public ApplicationDefaultGetDto(Application application) {
        super(application);
        this.user = new UserDefaultGetDto(application.getUser());
        images = new HashSet<>();
        installers = new HashSet<>();
        application.getImages().stream().map(ImageDto::new).forEach(images::add);
        application.getInstallers().stream().map(InstallerDto::new).forEach(installers::add);
    }
}