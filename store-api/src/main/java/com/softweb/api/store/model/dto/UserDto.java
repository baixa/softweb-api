package com.softweb.api.store.model.dto;

import com.softweb.api.store.model.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link User} entity
 */
@Data
public class UserDto implements Serializable {
    private final Long id;
    private final String username;
    private final String fullName;
    private final String password;
    private final Date lastEntered;
    private final boolean isEnabled;
    private final AuthorityDto authorityDto;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.password = user.getPassword();
        this.lastEntered = user.getLastEntered();
        this.authorityDto = new AuthorityDto(user.getAuthority());
        this.isEnabled = user.isEnabled();
    }
}