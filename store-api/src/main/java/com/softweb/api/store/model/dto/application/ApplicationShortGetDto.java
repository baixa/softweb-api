package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApplicationShortGetDto extends AbstractApplicationGetDto {
    public ApplicationShortGetDto(Application application) {
        super(application);
    }
}
