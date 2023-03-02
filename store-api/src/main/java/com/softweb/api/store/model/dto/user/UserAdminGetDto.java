package com.softweb.api.store.model.dto.user;

import com.softweb.api.store.model.entities.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Getter
public class UserAdminGetDto extends AbstractUserGetDto implements Serializable {
    private final String password;
    private final boolean isEnabled;

    public UserAdminGetDto(User user) {
        super(user);
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();
    }
}