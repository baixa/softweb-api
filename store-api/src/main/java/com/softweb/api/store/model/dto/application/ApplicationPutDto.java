package com.softweb.api.store.model.dto.application;

import com.softweb.api.store.model.entities.License;
import lombok.Data;

import java.util.Date;

@Data
public class ApplicationPutDto {
    private Long id;
    private final String name;
    private final String shortDescription;
    private final String longDescription;
    private final String logoBase64;
    private final Date lastUpdate;
    private final String licenseCode;
    private License license;

    public ApplicationPutDto(Long id, String name, String shortDescription, String longDescription, String logoBase64, String licenseCode) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.logoBase64 = logoBase64;
        this.licenseCode = licenseCode;
        this.lastUpdate = new Date();
    }
}
