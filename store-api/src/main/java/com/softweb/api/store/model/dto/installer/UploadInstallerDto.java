package com.softweb.api.store.model.dto.installer;

import com.softweb.api.store.model.dto.application.ApplicationShortGetDto;
import com.softweb.api.store.model.dto.operatingsystem.OperatingSystemDto;
import com.softweb.api.store.model.entities.Application;
import com.softweb.api.store.model.entities.OperatingSystem;
import lombok.Getter;

@Getter
public class UploadInstallerDto {
    private final Long id;
    private final ApplicationShortGetDto applicationDto;
    private final OperatingSystemDto operatingSystemDto;
    private final String fileName;
    private final String fileDownloadUri;
    private final String fileType;
    private final long size;

    public UploadInstallerDto(Long id, String fileName, String fileDownloadUri, String fileType, long size, Application application, OperatingSystem system) {
        this.id = id;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.applicationDto = new ApplicationShortGetDto(application);
        this.operatingSystemDto = new OperatingSystemDto(system);
    }


}
