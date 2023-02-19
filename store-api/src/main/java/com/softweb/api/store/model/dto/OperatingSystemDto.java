package com.softweb.api.store.model.dto;

import com.softweb.api.store.model.entities.OperatingSystem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * A DTO for the {@link OperatingSystem} entity
 */
@Data
public class OperatingSystemDto implements Serializable {
    private final Long id;
    private final String name;

    public OperatingSystemDto(OperatingSystem system) {
        this.id = system.getId();
        this.name = system.getName();
    }
}