package com.softweb.api.store.model.dto.image;

import com.softweb.api.store.model.dto.application.ApplicationDefaultGetDto;
import com.softweb.api.store.model.entities.Application;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadImageDto {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private ApplicationDefaultGetDto applicationDto;

    public UploadImageDto(String fileName, String fileDownloadUri, String fileType, long size, Application application) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.applicationDto = new ApplicationDefaultGetDto(application);
    }
}
