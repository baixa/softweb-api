package com.softweb.api.store.model.dto;

import com.softweb.api.store.model.dto.ApplicationDto;
import com.softweb.api.store.model.dto.OperatingSystemDto;
import com.softweb.api.store.model.entities.Installer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * A DTO for the {@link Installer} entity
 */
@Data
public class InstallerDto implements Serializable {
    private final Long id;
    private final ApplicationDto applicationDto;
    private final OperatingSystemDto operatingSystemDto;
    private final String installerPath;
    private final String version;
    private final int size;

    public InstallerDto(Installer installer) {
        this.id = installer.getId();
        this.applicationDto = new ApplicationDto(installer.getApplication());
        this.operatingSystemDto = new OperatingSystemDto(installer.getSystem());
        this.installerPath = installer.getInstallerPath();
        this.version = installer.getVersion();
        this.size = installer.getSize();
    }
}