package com.softweb.api.store.model.dto;

import com.softweb.api.store.model.entities.Developer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link Developer} entity
 */
@Data
public class DeveloperDto implements Serializable {
    private final Long id;
    private final String username;
    private final String fullName;
    private final String password;
    private final boolean isAdmin;
    private final Date lastEntered;

    public DeveloperDto(Developer developer) {
        this.id = developer.getId();
        this.username = developer.getUsername();
        this.fullName = developer.getFullName();
        this.password = developer.getPassword();
        this.isAdmin = developer.isAdmin();
        this.lastEntered = developer.getLastEntered();
    }
}