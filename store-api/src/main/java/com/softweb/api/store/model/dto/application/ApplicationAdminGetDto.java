package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.image.ImageDto;
import com.softweb.api.store.model.dto.installer.InstallerDto;
import com.softweb.api.store.model.dto.user.UserAdminGetDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link Application} entity
 */
@Getter @Setter
public class ApplicationAdminGetDto extends AbstractApplicationGetDto implements Serializable {

    private final UserAdminGetDto user;
    private final Set<ImageDto> images;
    private final Set<InstallerDto> installers;

    public ApplicationAdminGetDto(Application application) {
        super(application);
        this.user = new UserAdminGetDto(application.getUser());
        images = new HashSet<>();
        installers = new HashSet<>();
        application.getImages().stream().map(ImageDto::new).forEach(images::add);
        application.getInstallers().stream().map(InstallerDto::new).forEach(installers::add);
    }
}