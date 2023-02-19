package com.softweb.api.store.model.dto;

import com.softweb.api.store.model.entities.Image;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Image} entity
 */
@Data
public class ImageDto implements Serializable {
    private final Long id;
    private final String path;

    public ImageDto(Image image) {
        this.id = image.getId();
        this.path = image.getPath();
    }
}