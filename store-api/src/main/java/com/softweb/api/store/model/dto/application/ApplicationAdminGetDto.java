package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.user.UserAdminGetDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link Application} entity
 */
@Getter @Setter
public class ApplicationAdminGetDto extends AbstractApplicationGetDto implements Serializable {

    private final UserAdminGetDto user;

    public ApplicationAdminGetDto(Application application) {
        super(application);
        this.user = new UserAdminGetDto(application.getUser());
    }
}