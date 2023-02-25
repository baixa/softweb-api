package com.softweb.api.store.model.dto.image;

import com.softweb.api.store.model.dto.application.ApplicationShortGetDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadImageDto {
    private Long id;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private ApplicationShortGetDto applicationDto;

    public UploadImageDto(Long id, String fileName, String fileDownloadUri, String fileType, long size, Application application) {
        this.id = id;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.applicationDto = new ApplicationShortGetDto(application);
    }
}
