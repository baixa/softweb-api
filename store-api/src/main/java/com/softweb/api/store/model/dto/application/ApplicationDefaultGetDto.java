package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.image.ImageDto;
import com.softweb.api.store.model.dto.installer.InstallerDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link Application} entity
 */
@Getter @Setter
public class ApplicationDefaultGetDto extends AbstractApplicationGetDto implements Serializable {
    private final Set<ImageDto> images;
    private final Set<InstallerDto> installers;

    public ApplicationDefaultGetDto(Application application) {
        super(application);
        images = new HashSet<>();
        installers = new HashSet<>();

        if (!Objects.isNull(application.getImages()))
            application.getImages().stream().map(ImageDto::new).forEach(images::add);

        if (!Objects.isNull(application.getInstallers()))
            application.getInstallers().stream().map(InstallerDto::new).forEach(installers::add);
    }
}