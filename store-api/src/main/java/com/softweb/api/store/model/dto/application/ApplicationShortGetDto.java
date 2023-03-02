package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.dto.user.UserDefaultGetDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApplicationShortGetDto extends AbstractApplicationGetDto {
    private final UserDefaultGetDto user;
    public ApplicationShortGetDto(Application application) {
        super(application);
        this.user = new UserDefaultGetDto(application.getUser());
    }
}
