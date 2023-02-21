package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.user.UserDefaultDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.Application} entity
 */
@Getter @Setter
public class ApplicationDefaultGetDto extends AbstractApplicationGetDto implements Serializable {
    private final UserDefaultDto user;

    public ApplicationDefaultGetDto(Application application) {
        super(application);
        this.user = new UserDefaultDto(application.getUser());
    }
}