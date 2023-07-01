package com.softweb.api.store.model.dto.user;

import com.softweb.api.store.model.dto.authority.AuthorityDto;
import com.softweb.api.store.model.entities.User;
import com.softweb.api.store.utils.NumParser;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link com.softweb.api.store.model.entities.User} entity
 */
@Getter
public class UserGetDto implements Serializable {

    private final Long id;
    private final String username;
    private final String fullName;
    private final int[] lastEntered;
    private final AuthorityDto authority;

    public UserGetDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.authority = new AuthorityDto(user.getAuthority());
        this.lastEntered = NumParser.parseDateToNumArray(user.getLastEntered().plusHours(3));
    }
}