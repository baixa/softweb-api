package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.entities.Application;
import lombok.Data;

@Data
public class ApplicationSearch {
    private final Long id;
    private final String name;

    public ApplicationSearch (Application application) {
        this.id = application.getId();
        this.name = application.getName();
    }
}
