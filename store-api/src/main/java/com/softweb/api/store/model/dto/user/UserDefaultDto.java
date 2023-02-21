package com.softweb.api.store.model.dto.user;

import com.softweb.api.store.model.dto.authority.AuthorityDto;
import com.softweb.api.store.model.entities.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.User} entity
 */
@Data
public class UserDefaultDto implements Serializable {
    private final Long id;
    private final String username;
    private final String fullName;
    private final Date lastEntered;
    private final AuthorityDto authority;

    public UserDefaultDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.lastEntered = user.getLastEntered();
        this.authority = new AuthorityDto(user.getAuthority());
    }
}