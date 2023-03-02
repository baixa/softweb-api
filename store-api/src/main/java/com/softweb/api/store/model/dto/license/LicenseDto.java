package com.softweb.api.store.model.dto.license;

import com.softweb.api.store.model.entities.License;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link License} entity
 */
@Data
public class LicenseDto implements Serializable {
    private final String code;
    private final String name;

    public LicenseDto(License license) {
        this.code = license.getCode();
        this.name = license.getName();
    }
}